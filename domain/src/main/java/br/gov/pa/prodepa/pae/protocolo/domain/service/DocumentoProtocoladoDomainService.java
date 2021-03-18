package br.gov.pa.prodepa.pae.protocolo.domain.service;

import java.util.Arrays;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.pa.prodepa.nucleopa.client.MunicipioBasicDto;
import br.gov.pa.prodepa.pae.documento.client.DocumentoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocolarDocumentoDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocolarDocumentoEnriquecoDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.domain.exception.DomainException;
import br.gov.pa.prodepa.pae.protocolo.domain.exception.EntityNotFoundException;
import br.gov.pa.prodepa.pae.protocolo.domain.model.DocumentoProtocolado;
import br.gov.pa.prodepa.pae.protocolo.domain.model.NumeroDocumentoReservado;
import br.gov.pa.prodepa.pae.protocolo.domain.model.SequencialDocumento;
import br.gov.pa.prodepa.pae.protocolo.domain.model.TipoDestino;
import br.gov.pa.prodepa.pae.protocolo.domain.model.Usuario;
import br.gov.pa.prodepa.pae.protocolo.domain.port.DocumentoProtocoladoRepository;
import br.gov.pa.prodepa.pae.protocolo.domain.port.MessagingService;
import br.gov.pa.prodepa.pae.protocolo.domain.port.PaeDocumentoService;
import br.gov.pa.prodepa.pae.protocolo.domain.port.SequencialDocumentoRepository;
import br.gov.pa.prodepa.pae.suporte.client.LocalizacaoBasicDto;
import br.gov.pa.prodepa.pae.suporte.client.OrgaoPaeBasicDto;

public class DocumentoProtocoladoDomainService implements ProtocoloService {

	private final MessagingService messagingService;
	private final Usuario usuarioLogado;
	private final SequencialDocumentoRepository sequencialRepository;
	private final PaeDocumentoService documentoService;
	private final DocumentoProtocoladoRepository documentoRepository;
	private final SequencialDocumentoService sequencialDocumentoService;
	
	public DocumentoProtocoladoDomainService(MessagingService messagingService, Usuario usuarioLogado,
			SequencialDocumentoRepository sequencialRepository,
			PaeDocumentoService documentoService, 
			DocumentoProtocoladoRepository documentoRepository, 
			SequencialDocumentoService sequencialDocumentoService) {
		super();
		this.messagingService = messagingService;
		this.usuarioLogado = usuarioLogado;
		this.sequencialRepository = sequencialRepository;
		this.documentoService = documentoService;
		this.documentoRepository = documentoRepository;
		this.sequencialDocumentoService = sequencialDocumentoService;
	}

	public void protocolarDocumento(ProtocolarDocumentoDto dto) {
		String documentoEnriquecidoJson = messagingService.enriquecerModeloDadosSuporte(dto);
		
		// CONTROLE ACESSO
		// TODO checar se os assinantes (usuarios do CA) existem

		// NUCLEO-PA
		// TODO checar se o municipio existe
		// TODO checar se os interessados (pessoa fisica e juridica do nucleopa) existem
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		ProtocolarDocumentoEnriquecoDto documentoEnrirequecidoDto = null;
		try {
			documentoEnrirequecidoDto = mapper.readValue(documentoEnriquecidoJson, ProtocolarDocumentoEnriquecoDto.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		
		documentoEnrirequecidoDto.setAssinantess(Arrays.asList(Usuario.builder().id(2L).nome("usuario teste").build()));
		documentoEnrirequecidoDto.setComplemento(dto.getComplemento());
		
		documentoEnrirequecidoDto.setInteressadosPessoasFisicas(null);
		documentoEnrirequecidoDto.setInteressadosPessoasJuricas(null);
		
		//TODO Alterar para consulta servico nucleopa
		documentoEnrirequecidoDto.setMunicipio(MunicipioBasicDto.builder().id(1L).nome("Belem").build());
		
		documentoEnrirequecidoDto.setNumeroReservadoId(dto.getNumeroReservadoId());
		documentoEnrirequecidoDto.setOrigemDocumento(dto.getOrigemDocumento());
		
		documentoEnrirequecidoDto.setPrioridade(dto.getPrioridade());
		documentoEnrirequecidoDto.setTipoDestino(dto.getTipoDestino());
		
		//TODO transformar em comunicacao usando mensageria
		documentoEnrirequecidoDto.setDocumento(documentoService.buscarPorId(dto.getDocumentoId()));
		
		protocolarDocumento(dto, documentoEnrirequecidoDto);
	}
	
	public void protocolarDocumento(ProtocolarDocumentoDto dto, ProtocolarDocumentoEnriquecoDto dtoEnriquecido) {
		
		DocumentoProtocolado dp = DocumentoProtocolado.buildFrom(dto);
		
		if (dto.getNumeroReservadoId() == null) {
			// essa consulta executa em uma transacao independente
			SequencialDocumento sequencial = sequencialDocumentoService.buscarProximoSequencial(dp.getEspecieId(), dp.getLocalizacaoOrigemId());
			dp.setAno(sequencial.getAno());
			dp.setSequencial(sequencial.getSequencial());
		} else {
			NumeroDocumentoReservado numeroReservado = sequencialRepository
					.buscarNumeroReservado(dto.getNumeroReservadoId());
			if (numeroReservado.getDocumentoProtocolado() != null) {
				throw new DomainException("O número reservado escolhido já foi usado por outra pessoa.");
			}
		}

		dp.setDataCadastro(new Date());
		dp.setConteudoDocumento(dtoEnriquecido.getDocumento().getConteudo());
		dp.setModeloConteudoId(dtoEnriquecido.getDocumento().getModeloConteudo().getId());

		DocumentoProtocolado documentoProtocoladoSalvo = documentoRepository.salvar(dp);
		
		for (Long orgaoId : dto.getDestinosIds()) {
			ProtocoloDto protocoloDto = new ProtocoloDto();
			protocoloDto.setAssunto(dtoEnriquecido.getAssunto());
			protocoloDto.setEspecie(dtoEnriquecido.getEspecie());
			
			DocumentoBasicDto dpDto = new DocumentoBasicDto();
			dpDto.setAno(documentoProtocoladoSalvo.getAno());
			dpDto.setSequencial(documentoProtocoladoSalvo.getSequencial());
			dpDto.setId(documentoProtocoladoSalvo.getId());
			protocoloDto.setDocumentoProtocolado(dpDto);
			
			protocoloDto.setComplemento(documentoProtocoladoSalvo.getComplemento());
			protocoloDto.setLocalizacaoOrigem(dtoEnriquecido.getLocalizacaoOrigem());
			protocoloDto.setOrgaoOrigem(dtoEnriquecido.getOrgaoOrigem());
			protocoloDto.setOrigemDocumento(dtoEnriquecido.getOrigemDocumento());
			protocoloDto.setPrioridade(dtoEnriquecido.getPrioridade());
			protocoloDto.setMunicipio(dtoEnriquecido.getMunicipio());
			protocoloDto.setUsuarioCadastro(usuarioLogado);
			
			protocoloDto.setInteressadosPessoasFisicas(dtoEnriquecido.getInteressadosPessoasFisicas());
			protocoloDto.setInteressadosPessoasJuricas(dtoEnriquecido.getInteressadosPessoasJuricas());
			protocoloDto.setOrgaosInteressados(dtoEnriquecido.getOrgaosInteressados());
			protocoloDto.setLocalizacoesInteressadas(dtoEnriquecido.getLocalizacoesInteressadas());
			
			protocoloDto.setAssinantes(dtoEnriquecido.getAssinantess());
			
			if (dp.getTipoDestino().equals(TipoDestino.ORGAO)) {
				OrgaoPaeBasicDto orgao = dtoEnriquecido.getOrgaosDestinos()
						.stream()
						.filter( o -> o.getId().equals(orgaoId))
						.findFirst()
						.orElseThrow( () -> new EntityNotFoundException("Nao foi encontrado o orgao com o id " + orgaoId));
				protocoloDto.setOrgaoDestino(orgao);
			}

			if (dp.getTipoDestino().equals(TipoDestino.SETOR)) {
				LocalizacaoBasicDto localizacao = dtoEnriquecido.getLocalizacoesDestino().stream().filter( o -> o.getId() == orgaoId).findFirst().get();
				protocoloDto.setLocalizacaoDestino(localizacao);
			}
			messagingService.sendMessage(protocoloDto);
		}
	}
}
