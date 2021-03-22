package br.gov.pa.prodepa.pae.protocolo.domain.service;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import br.gov.pa.prodepa.nucleopa.client.MunicipioBasicDto;
import br.gov.pa.prodepa.nucleopa.client.PessoaFisicaBasicDto;
import br.gov.pa.prodepa.nucleopa.client.PessoaJuridicaBasicDto;
import br.gov.pa.prodepa.pae.documento.client.DocumentoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocolarDocumentoDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.domain.model.DocumentoProtocolado;
import br.gov.pa.prodepa.pae.protocolo.domain.model.NumeroDocumentoReservado;
import br.gov.pa.prodepa.pae.protocolo.domain.model.SequencialDocumento;
import br.gov.pa.prodepa.pae.protocolo.domain.model.TipoDestino;
import br.gov.pa.prodepa.pae.protocolo.domain.model.Usuario;
import br.gov.pa.prodepa.pae.protocolo.domain.port.DocumentoProtocoladoRepository;
import br.gov.pa.prodepa.pae.protocolo.domain.port.MessagingService;
import br.gov.pa.prodepa.pae.protocolo.domain.port.PaeDocumentoService;
import br.gov.pa.prodepa.pae.protocolo.domain.port.SequencialDocumentoRepository;
import br.gov.pa.prodepa.pae.suporte.client.PaeSuporteEnricherDto;

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

	public PaeSuporteEnricherDto enriquecerDadosPaeSuporte(ProtocolarDocumentoDto dto) {
		
		PaeSuporteEnricherDto enricher = new PaeSuporteEnricherDto();
		enricher.addEspecieId(dto.getEspecieId());
		enricher.addAssuntoId(dto.getAssuntoId());
		enricher.addOrgaoId(dto.getInteressadosOrgaosIds());
		enricher.addLocalizacaoId(dto.getInteressadosLocalizacoesIds());
		enricher.addLocalizacaoId(dto.getLocalizacaoOrigemId());
		enricher.addOrgaoId(dto.getOrgaoOrigemId());
		
		if(dto.getTipoDestino().equals(TipoDestino.ORGAO)) {
			enricher.addOrgaoId(dto.getDestinosIds());
		} else {
			enricher.addLocalizacaoId(dto.getDestinosIds());
		}
		
		PaeSuporteEnricherDto enrichedParams = messagingService.enriquecerModeloDadosSuporte(enricher);
		
		// CONTROLE ACESSO
		// TODO checar se os assinantes (usuarios do CA) existem

		// NUCLEO-PA
		// TODO checar se o municipio existe
		// TODO checar se os interessados (pessoa fisica e juridica do nucleopa) existem
		
		return enrichedParams;
	}
	
	public void protocolarDocumento(ProtocolarDocumentoDto dto) {
		
		PaeSuporteEnricherDto dtoEnriquecido = enriquecerDadosPaeSuporte(dto);
		validarExistenciaEntidadesPaeSuporte(dtoEnriquecido, dto);
		
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
				//throw new DomainException("O número reservado escolhido já foi usado por outra pessoa.");
			}
		}

		dp.setDataCadastro(new Date());
		
		DocumentoBasicDto documento = documentoService.buscarDocumentoPorId(dp.getDocumentoId());
		documento.getModeloConteudo().setModeloEstrutura(documentoService.buscarModeloEstruturaPorId(documento.getModeloConteudo().getId()));
		
		dp.setConteudoDocumento(documento.getConteudo());
		dp.setModeloConteudoId(documento.getModeloConteudo().getId());

		DocumentoProtocolado documentoProtocoladoSalvo = documentoRepository.salvar(dp);
		
		UUID correlationId = UUID.randomUUID();
		
		for (Long destinoId : dp.getDestinosIds()) {
			ProtocoloDto protocoloDto = new ProtocoloDto();
			protocoloDto.setAssunto(dtoEnriquecido.getAssunto(dp.getAssuntoId()));
			protocoloDto.setEspecie(dtoEnriquecido.getEspecie(dp.getEspecieId()));
			
			documento.setAno(documentoProtocoladoSalvo.getAno());
			documento.setSequencial(documentoProtocoladoSalvo.getSequencial());
			protocoloDto.setDocumentoProtocolado(documento);
			
			protocoloDto.setComplemento(documentoProtocoladoSalvo.getComplemento());
			protocoloDto.setLocalizacaoOrigem(dtoEnriquecido.getLocalizacao(dp.getLocalizacaoOrigemId()));
			protocoloDto.setOrgaoOrigem(dtoEnriquecido.getOrgao(dp.getOrgaoOrigemId()));
			protocoloDto.setOrigemDocumento(dp.getOrigemDocumento());
			protocoloDto.setPrioridade(dp.getPrioridade());
			protocoloDto.setMunicipio(new MunicipioBasicDto(1L, "Belem"));
			protocoloDto.setUsuarioCadastro(usuarioLogado);
			
			//TODO alterar 
			protocoloDto.setInteressadosPessoasFisicas(dp.getPessoasFisicasInteressadasIds().stream().map( id -> PessoaFisicaBasicDto.builder().id(id).build()).collect(Collectors.toList()));
			protocoloDto.setInteressadosPessoasJuricas(dp.getPessoasJuridicasInteressadasIds().stream().map( id -> PessoaJuridicaBasicDto.builder().id(id).build()).collect(Collectors.toList()));
			
			protocoloDto.setOrgaosInteressados(dtoEnriquecido.getOrgaos(dp.getOrgaosInteressadosIds()));
			protocoloDto.setLocalizacoesInteressadas(dtoEnriquecido.getLocalizacoes(dp.getLocalizacoesInteressadasIds()));
			
			//TODO alterar  
			protocoloDto.setAssinantes(dp.getAssinantesIds().stream().map( id -> Usuario.builder().id(id).build()).collect(Collectors.toList()));
			
			if (dp.getTipoDestino().equals(TipoDestino.ORGAO)) {
				protocoloDto.setOrgaoDestino(dtoEnriquecido.getOrgao(destinoId));
			}

			if (dp.getTipoDestino().equals(TipoDestino.SETOR)) {
				protocoloDto.setLocalizacaoDestino(dtoEnriquecido.getLocalizacao(destinoId));
			}
			messagingService.enviarParaFilaProtocolarDocumento(protocoloDto, correlationId.toString());
		}
	}
	
	private void validarExistenciaEntidadesPaeSuporte(PaeSuporteEnricherDto enrichedParams, ProtocolarDocumentoDto dto) {
		/*
		DomainException de = new DomainException();
		
		if(!enrichedParams.contemAssuntoComId(dto.getAssuntoId())) {
			de.addError("O assunto selecionado nao existe [id: " + dto.getAssuntoId() + "]" );
		}
		
		if(!enrichedParams.contemEspecieComId(dto.getEspecieId())) {
			de.addError("A especie selecionada nao existe [id: " + dto.getEspecieId() + "]" );
		}
		
		for(Long id : dto.getInteressadosOrgaosIds()) {
			if(!enrichedParams.contemOrgaoComId(id)) {
				de.addError("O orgao interessado selecionado nao existe [id: " + id + "]" );
			}
		}
		
		for(Long id : dto.getInteressadosLocalizacoesIds()) {
			if(!enrichedParams.contemLocalizacaoComId(id)) {
				de.addError("A localizacao interessada selecionada nao existe [id: " + id + "]" );
			}
		}
		
		if(!enrichedParams.contemLocalizacaoComId(dto.getLocalizacaoOrigemId())) {
			de.addError("A localizacao origem selecionada nao existe [id: " + dto.getLocalizacaoOrigemId() + "]" );
		}
		
		if(!enrichedParams.contemOrgaoComId(dto.getOrgaoOrigemId())) {
			de.addError("O orgao origem selecionado nao existe [id: " + dto.getOrgaoOrigemId() + "]" );
		}
		
		if(dto.getTipoDestino().equals(TipoDestino.ORGAO)) {
			for(Long id : dto.getDestinosIds()) {
				if(!enrichedParams.contemOrgaoComId(id)) {
					de.addError("O orgao destino selecionado nao existe [id: " + id + "]" );
				}
			}
		} else {
			for(Long id : dto.getDestinosIds()) {
				if(!enrichedParams.contemLocalizacaoComId(id)) {
					de.addError("A localizacao destino selecionado nao existe [id: " + id + "]" );
				}
			}
		}
		
		de.throwException();*/
	}
}
