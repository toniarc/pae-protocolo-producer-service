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
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.AssuntoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.EspecieBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.LocalizacaoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.OrgaoPaeBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.model.DocumentoProtocolado;
import br.gov.pa.prodepa.pae.protocolo.domain.model.NumeroDocumentoReservado;
import br.gov.pa.prodepa.pae.protocolo.domain.model.SequencialDocumento;
import br.gov.pa.prodepa.pae.protocolo.domain.model.TipoDestino;
import br.gov.pa.prodepa.pae.protocolo.domain.port.DocumentoProtocoladoRepository;
import br.gov.pa.prodepa.pae.protocolo.domain.port.NucleopaRestClient;
import br.gov.pa.prodepa.pae.protocolo.domain.port.PaeDocumentoService;
import br.gov.pa.prodepa.pae.protocolo.domain.port.PaeSuporteService;
import br.gov.pa.prodepa.pae.protocolo.domain.port.ProtocoloProducerMessageService;
import br.gov.pa.prodepa.pae.protocolo.domain.port.SequencialDocumentoRepository;
import br.gov.pa.prodepa.pae.protocolo.domain.validator.ProtocoloUtil;
import br.gov.pa.prodepa.pae.protocolo.domain.validator.ProtocoloValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DocumentoProtocoladoDomainService implements DocumentoProtocoladoService {

	private final ProtocoloProducerMessageService protocoloProducerMessageService;
	private final UsuarioDto usuarioLogado;
	private final SequencialDocumentoRepository sequencialRepository;
	private final PaeDocumentoService documentoService;
	private final DocumentoProtocoladoRepository documentoRepository;
	private final SequencialDocumentoService sequencialDocumentoService;
	private final NucleopaRestClient nucleopaService;
	private final PaeSuporteService suporteSevice;
	
	public void protocolarDocumento(ProtocolarDocumentoDto dto) {

		EspecieBasicDto especie = suporteSevice.buscarEspecie(dto.getEspecieId());
		AssuntoBasicDto assunto = suporteSevice.buscarAssunto(dto.getAssuntoId());
		List<OrgaoPaeBasicDto> orgaos = suporteSevice.buscarOrgaos(dto.getOrgaosIds());  
		List<LocalizacaoBasicDto> localizacoes = suporteSevice.buscarLocalizacoes(dto.getLocalizacoesIds());
		
		DocumentoBasicDto documento = documentoService.buscarDocumentoPorId(dto.getDocumentoId());
		documento.getModeloConteudo().setModeloEstrutura(documentoService.buscarModeloEstruturaPorId(documento.getModeloConteudo().getId()));
		
		ProtocoloValidator.getInstance(dto)
			.validarCamposDinamicosUsadosNoDocumento(documento.getConteudo(), dto.getTipoDestino())
			.validarCamposDinamicosUsadosNoDocumento(documento.getModeloConteudo().getModeloEstrutura().getCabecalho(), dto.getTipoDestino())
			.validarCamposDinamicosUsadosNoDocumento(documento.getModeloConteudo().getModeloEstrutura().getRodape(), dto.getTipoDestino())
			.validarSeAEspecieExiste(especie)
			.validarSeOAssuntoExiste(assunto)
			.validarSeOOrgaoInformadoComoInteressadoExiste(orgaos)
			.validarSeOrgaoOrigemInformadoExiste(orgaos)
			.validarSeOsDestinosInformadosExistem(orgaos, localizacoes)
			.validarSeAsLocalizacoesInteressadasExistem(localizacoes)
			.validarSeALocalizacaoOrigemInformadaExiste(localizacoes)
			.validarSeTodosOsOrgaoPossuemSetorPadraoComResponsavel(orgaos)
		.validar();
		
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
			protocoloDto.setAssunto(assunto);
			protocoloDto.setEspecie(especie);
			
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
			protocoloDto.setLocalizacaoOrigem(getLocalizacao(localizacoes, dp.getLocalizacaoOrigemId()));
			protocoloDto.setOrgaoOrigem(getOrgao(orgaos, dto.getOrgaoOrigemId()));
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
			
			protocoloDto.setOrgaosInteressados(getOrgaos(orgaos, dp.getOrgaosInteressadosIds()));
			protocoloDto.setLocalizacoesInteressadas(getLocalizacoes(localizacoes, dp.getLocalizacoesInteressadasIds()));
			
			//TODO alterar  
			protocoloDto.setAssinantes(dp.getUsuariosQueDevemAssinar().stream().map( id -> UsuarioDto.builder().id(id).build()).collect(Collectors.toList()));
			
			if (dp.getTipoDestino().equals(TipoDestino.ORGAO)) {
				protocoloDto.setOrgaoDestino(getOrgao(orgaos, destinoId));
			}

			if (dp.getTipoDestino().equals(TipoDestino.SETOR)) {
				protocoloDto.setLocalizacaoDestino(getLocalizacao(localizacoes, destinoId));
			}
			
			ProtocoloUtil.substituirCamposDinamicos(protocoloDto);
			
			protocoloProducerMessageService.enviarParaFilaProtocolarDocumento(protocoloDto, correlationId.toString());
		}
	}
	
	private List<LocalizacaoBasicDto> getLocalizacoes(List<LocalizacaoBasicDto> localizacoes, List<Long> ids) {
		return localizacoes.stream()
				.filter( a -> ids.contains(a.getId()))
				.collect(Collectors.toList());
	}

	private OrgaoPaeBasicDto getOrgao(List<OrgaoPaeBasicDto> orgaos, Long id) {
		return orgaos.stream()
				.filter( a -> a.getId().equals(id))
				.findFirst()
				.get();
	}

	public LocalizacaoBasicDto getLocalizacao(List<LocalizacaoBasicDto> localizacoes, Long id) {
		return localizacoes.stream()
				.filter( a -> a.getId().equals(id))
				.findFirst()
				.get();
	}

	public List<OrgaoPaeBasicDto> getOrgaos(List<OrgaoPaeBasicDto> orgaos, List<Long> ids) {
		return orgaos.stream()
			.filter( a -> ids.contains(a.getId()))
			.collect(Collectors.toList());
	}

}
