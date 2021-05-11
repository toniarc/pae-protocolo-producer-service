package br.gov.pa.prodepa.pae.protocolo.domain.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.gov.pa.prodepa.pae.common.domain.dto.UsuarioDto;
import br.gov.pa.prodepa.pae.common.domain.exception.DomainException;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocolarDocumentoDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.documento.DocumentoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.documento.DocumentoProtocoladoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.MunicipioBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.PessoaFisicaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.PessoaJuridicaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.PaeSuporteEnricherDto;
import br.gov.pa.prodepa.pae.protocolo.domain.model.DocumentoProtocolado;
import br.gov.pa.prodepa.pae.protocolo.domain.model.NumeroDocumentoReservado;
import br.gov.pa.prodepa.pae.protocolo.domain.model.SequencialDocumento;
import br.gov.pa.prodepa.pae.protocolo.domain.model.TipoDestino;
import br.gov.pa.prodepa.pae.protocolo.domain.port.DocumentoProtocoladoRepository;
import br.gov.pa.prodepa.pae.protocolo.domain.port.MessagingService;
import br.gov.pa.prodepa.pae.protocolo.domain.port.NucleopaRestClient;
import br.gov.pa.prodepa.pae.protocolo.domain.port.PaeDocumentoService;
import br.gov.pa.prodepa.pae.protocolo.domain.port.SequencialDocumentoRepository;
import br.gov.pa.prodepa.pae.protocolo.domain.validator.ProtocoloUtil;

public class DocumentoProtocoladoDomainService implements ProtocoloService {

	private final MessagingService messagingService;
	private final UsuarioDto usuarioLogado;
	private final SequencialDocumentoRepository sequencialRepository;
	private final PaeDocumentoService documentoService;
	private final DocumentoProtocoladoRepository documentoRepository;
	private final SequencialDocumentoService sequencialDocumentoService;
	private final NucleopaRestClient nucleopaService;
	
	public DocumentoProtocoladoDomainService(MessagingService messagingService, UsuarioDto usuarioLogado,
			SequencialDocumentoRepository sequencialRepository,
			PaeDocumentoService documentoService, 
			DocumentoProtocoladoRepository documentoRepository, 
			SequencialDocumentoService sequencialDocumentoService,
			NucleopaRestClient nucleopaService) {
		super();
		this.messagingService = messagingService;
		this.usuarioLogado = usuarioLogado;
		this.sequencialRepository = sequencialRepository;
		this.documentoService = documentoService;
		this.documentoRepository = documentoRepository;
		this.sequencialDocumentoService = sequencialDocumentoService;
		this.nucleopaService = nucleopaService;
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
		
		return enrichedParams;
	}
	
	public void protocolarDocumento(ProtocolarDocumentoDto dto) {

		DocumentoBasicDto documento = documentoService.buscarDocumentoPorId(dto.getDocumentoId());
		documento.getModeloConteudo().setModeloEstrutura(documentoService.buscarModeloEstruturaPorId(documento.getModeloConteudo().getId()));
		
		ProtocoloUtil.validarCamposDinamicosUsadosNoDocumento(documento.getConteudo(), dto.getTipoDestino());
		ProtocoloUtil.validarCamposDinamicosUsadosNoDocumento(documento.getModeloConteudo().getModeloEstrutura().getCabecalho(), dto.getTipoDestino());
		ProtocoloUtil.validarCamposDinamicosUsadosNoDocumento(documento.getModeloConteudo().getModeloEstrutura().getRodape(), dto.getTipoDestino());
		
		PaeSuporteEnricherDto dtoEnriquecido = enriquecerDadosPaeSuporte(dto);
		validarExistenciaEntidadesPaeSuporte(dtoEnriquecido, dto);
		
		List<PessoaFisicaBasicDto> pessoasFisicas = nucleopaService.buscarPessoaFisicaPorId(new HashSet<Long>(dto.getInteressadosPessoasFisicasIds()));
		List<PessoaJuridicaBasicDto> pessoasJuridicas = nucleopaService.buscarPessoaJuridicaPorId(new HashSet<Long>(dto.getInteressadosPessoasJuridicasIds()));
		List<MunicipioBasicDto> municipios = nucleopaService.buscarMunicipioPorId(new HashSet<Long>(Arrays.asList( dto.getMunicipioId())));
		
		DocumentoProtocolado dp = DocumentoProtocolado.buildFrom(dto);
		
		if (dto.getNumeroReservadoId() == null) {
			// essa consulta executa em uma transacao independente
			SequencialDocumento sequencial = sequencialDocumentoService.buscarProximoSequencial(dp.getEspecieId(), dp.getLocalizacaoOrigemId());
			dp.setAno(sequencial.getAno());
			dp.setSequencial(sequencial.getSequencial());
		} else {
			NumeroDocumentoReservado numeroReservado = sequencialRepository.buscarNumeroReservado(dto.getNumeroReservadoId());
			if (numeroReservado.getDocumentoProtocolado() != null) {
				throw new DomainException("O número reservado escolhido já foi usado por outra pessoa.");
			}
		}

		dp.setDataCadastro(new Date());
		
		dp.setConteudoDocumento(documento.getConteudo());
		dp.setModeloConteudoId(documento.getModeloConteudo().getId());

		DocumentoProtocolado documentoProtocoladoSalvo = documentoRepository.salvar(dp);
		
		UUID correlationId = UUID.randomUUID();
		
		for (Long destinoId : dp.getDestinosIds()) {
			ProtocoloDto protocoloDto = new ProtocoloDto();
			protocoloDto.setTipoDestino(dp.getTipoDestino());
			protocoloDto.setAssunto(dtoEnriquecido.getAssunto(dp.getAssuntoId()));
			protocoloDto.setEspecie(dtoEnriquecido.getEspecie(dp.getEspecieId()));
			
			documento.setAno(documentoProtocoladoSalvo.getAno());
			documento.setSequencial(documentoProtocoladoSalvo.getSequencial());
			protocoloDto.setDocumento(documento);
			protocoloDto.setDocumentoProtocolado(DocumentoProtocoladoBasicDto.builder()
					.ano(documentoProtocoladoSalvo.getAno())
					.id(documentoProtocoladoSalvo.getId())
					.numero(documentoProtocoladoSalvo.getSequencial())
					.dataCadastro(documentoProtocoladoSalvo.getDataCadastro())
					.build());
			
			protocoloDto.setComplemento(documentoProtocoladoSalvo.getComplemento());
			protocoloDto.setLocalizacaoOrigem(dtoEnriquecido.getLocalizacao(dp.getLocalizacaoOrigemId()));
			protocoloDto.setOrgaoOrigem(dtoEnriquecido.getOrgao(dp.getOrgaoOrigemId()));
			protocoloDto.setOrigemDocumento(dp.getOrigemDocumento());
			protocoloDto.setPrioridade(dp.getPrioridade());
			protocoloDto.setMunicipio( municipios.stream().filter( m -> m.getId().equals(dto.getMunicipioId())).findFirst().get() );
			protocoloDto.setUsuarioCadastro(usuarioLogado);
			
			//TODO alterar 
			protocoloDto.setInteressadosPessoasFisicas(dp.getPessoasFisicasInteressadasIds().stream().map( id -> {
				return pessoasFisicas.stream().filter( pf -> pf.getId().equals(id)).findFirst().get();
			}).collect(Collectors.toList()));
			
			protocoloDto.setInteressadosPessoasJuricas(dp.getPessoasJuridicasInteressadasIds().stream().map( id -> {
				return pessoasJuridicas.stream().filter( pf -> pf.getId().equals(id)).findFirst().get();
			}).collect(Collectors.toList()));
			
			protocoloDto.setOrgaosInteressados(dtoEnriquecido.getOrgaos(dp.getOrgaosInteressadosIds()));
			protocoloDto.setLocalizacoesInteressadas(dtoEnriquecido.getLocalizacoes(dp.getLocalizacoesInteressadasIds()));
			
			//TODO alterar  
			protocoloDto.setAssinantes(dp.getAssinantesIds().stream().map( id -> UsuarioDto.builder().id(id).build()).collect(Collectors.toList()));
			
			if (dp.getTipoDestino().equals(TipoDestino.ORGAO)) {
				protocoloDto.setOrgaoDestino(dtoEnriquecido.getOrgao(destinoId));
			}

			if (dp.getTipoDestino().equals(TipoDestino.SETOR)) {
				protocoloDto.setLocalizacaoDestino(dtoEnriquecido.getLocalizacao(destinoId));
			}
			
			ProtocoloUtil.substituirCamposDinamicos(protocoloDto);
			
			messagingService.enviarParaFilaProtocolarDocumento(protocoloDto, correlationId.toString());
		}
	}

	private void validarExistenciaEntidadesPaeSuporte(PaeSuporteEnricherDto enrichedParams, ProtocolarDocumentoDto dto) {
		
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
		
		de.throwException();
	}
	
}
