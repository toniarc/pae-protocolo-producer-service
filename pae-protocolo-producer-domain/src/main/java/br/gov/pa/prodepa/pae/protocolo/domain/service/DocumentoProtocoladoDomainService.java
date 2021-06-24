package br.gov.pa.prodepa.pae.protocolo.domain.service;

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
import br.gov.pa.prodepa.pae.protocolo.domain.port.AuditoriaService;
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
	//private final AuditoriaService auditoriaService;

	// Passo 1: buscar origem do documento RN01
	// Passo 2: buscar localizacoes do usuario (combo de localizacoes onde o documento sera protocolado)
	// Passo 3: buscar os tipos de destinos (RN05)
	// Passo 4: buscar destinos (RN03)
	// Passo 5: buscar estados
	// Passo 6: buscar municipios por estado
	// Passo 7: buscar interessados (pessoa fisica, pessoa juridica, setor e orgao)
	// Passo 8: buscar assinantes (usuarios)

	public void protocolarDocumento(ProtocolarDocumentoDto dto) {

		// TODO registrar auditoria

		ProtocoloValidator validator = ProtocoloValidator.getInstance(dto);

		validator
			.validarCamposObrigatorios()
		.validar();

		EspecieBasicDto especie = suporteSevice.buscarEspecie(dto.getEspecieId());
		AssuntoBasicDto assunto = suporteSevice.buscarAssunto(dto.getAssuntoId());
		List<OrgaoPaeBasicDto> orgaos = suporteSevice.buscarOrgaos(dto.getOrgaosIds());
		List<LocalizacaoBasicDto> localizacoes = suporteSevice.buscarLocalizacoes(dto.getLocalizacoesIds());
		List<LocalizacaoBasicDto> localizacoesUsuario = suporteSevice.buscarLocalizacoesUsuarioAtivas(usuarioLogado.getId());

		DocumentoBasicDto documento = documentoService.buscarDocumentoPorId(dto.getDocumentoId());
		documento.getModeloConteudo()
				.setModeloEstrutura(documentoService.buscarModeloEstruturaPorId(documento.getModeloConteudo().getId()));

		validator
			.verificarSeAEspeciePodeGerarProtocolo(especie)
			.validarAQuantidadeDeDestinosQueAEspeciePermite(especie)
			//.validarCamposDinamicosUsadosNoDocumento(documento.getConteudo(), dto.getTipoDestino())
			//.validarCamposDinamicosUsadosNoDocumento(documento.getModeloConteudo().getModeloEstrutura().getCabecalho(), dto.getTipoDestino())
			//.validarCamposDinamicosUsadosNoDocumento(documento.getModeloConteudo().getModeloEstrutura().getRodape(), dto.getTipoDestino())
			.validarSeAEspecieExiste(especie).validarSeOAssuntoExiste(assunto)
			.validarSeOOrgaoInformadoComoInteressadoExiste(orgaos).validarSeOrgaoOrigemInformadoExiste(orgaos)
			.validarSeOsDestinosInformadosExistem(orgaos, localizacoes)
			.validarSeAsLocalizacoesInteressadasExistem(localizacoes)
			.validarSeALocalizacaoOrigemInformadaExiste(localizacoes)
			.validarSeTodosOsOrgaoDestinosPossuemSetorPadraoComResponsavel(orgaos)

			.validarSeTodosOsSetoresDeDestinoSaoDoMesmoOrgaoDoUsuarioLogado(localizacoes, usuarioLogado)
			.validarSeTodosOsSetoresDeDestinoPossuemResponsavel(localizacoes)
			.validarSeAlgumaLocalizacaoDeDestinoEhIgualALocalizacaoDeOrigem(localizacoes)
			.validarSeOUsuarioPossuiAoMenosUmVinculoAtivo(localizacoesUsuario)
		.validar();

		List<PessoaFisicaBasicDto> pessoasFisicas = nucleopaService.buscarPessoaFisicaPorId(new HashSet<>(dto.getInteressadosPessoasFisicasIds()));
		List<PessoaJuridicaBasicDto> pessoasJuridicas = nucleopaService.buscarPessoaJuridicaPorId(new HashSet<>(dto.getInteressadosPessoasJuridicasIds()));
		MunicipioBasicDto municipio = nucleopaService.buscarMunicipioPorCodigoIbge(dto.getMunicipioIbge());

		DocumentoProtocolado dp = DocumentoProtocolado.buildFrom(dto);

		gerarNumeroDoDocumento(dto, dp);

		dp.setDataCadastro(new Date());
		dp.setConteudoDocumento(documento.getConteudo());
		dp.setModeloConteudoId(documento.getModeloConteudo().getId());
		dp.setJaFoiTramitado(false);

		DocumentoProtocolado documentoProtocoladoSalvo = documentoRepository.salvar(dp);

		UUID correlationId = UUID.randomUUID();

		for (Long destinoId : dp.getDestinosIds()) {
			ProtocoloDto protocoloDto = new ProtocoloDto();
			protocoloDto.setTipoDestino(dp.getTipoDestino());
			protocoloDto.setAssunto(assunto);
			protocoloDto.setEspecie(especie);

			documento.setAno(documentoProtocoladoSalvo.getAnoDocumento());
			documento.setSequencial(documentoProtocoladoSalvo.getNumeroDocumento());
			protocoloDto.setDocumento(documento);
			protocoloDto.setDocumentoProtocolado(
					DocumentoProtocoladoBasicDto.builder()
						.ano(documentoProtocoladoSalvo.getAnoDocumento())
						.id(documentoProtocoladoSalvo.getId())
						.numero(documentoProtocoladoSalvo.getNumeroDocumento())
						.dataCadastro(documentoProtocoladoSalvo.getDataCadastro())
						.build());

			protocoloDto.setComplemento(documentoProtocoladoSalvo.getComplemento());
			protocoloDto.setLocalizacaoOrigem(getLocalizacao(localizacoes, dp.getLocalizacaoOrigemId()));
			protocoloDto.setOrgaoOrigem(getOrgao(orgaos, dto.getOrgaoOrigemId()));
			protocoloDto.setOrigemDocumento(dp.getOrigemDocumento());
			protocoloDto.setPrioridade(dp.getPrioridade());
			protocoloDto.setMunicipio(municipio);
			protocoloDto.setUsuarioCadastro(usuarioLogado);

			protocoloDto.setInteressadosPessoasFisicas(dp.getPessoasFisicasInteressadasIds().stream().map(id -> {
				return pessoasFisicas.stream().filter(pf -> pf.getId().equals(id)).findFirst().get();
			}).collect(Collectors.toList()));

			protocoloDto.setInteressadosPessoasJuricas(dp.getPessoasJuridicasInteressadasIds().stream().map(id -> {
				return pessoasJuridicas.stream().filter(pf -> pf.getId().equals(id)).findFirst().get();
			}).collect(Collectors.toList()));

			protocoloDto.setOrgaosInteressados(getOrgaos(orgaos, dp.getOrgaosInteressadosIds()));
			protocoloDto.setLocalizacoesInteressadas(getLocalizacoes(localizacoes, dp.getLocalizacoesInteressadasIds()));

			// TODO alterar
			protocoloDto.setAssinantes(dp.getUsuariosQueDevemAssinar().stream().map(id -> UsuarioDto.builder().id(id).build()).collect(Collectors.toList()));

			if (dp.getTipoDestino().equals(TipoDestino.ORGAO)) {
				OrgaoPaeBasicDto orgaoDestino = getOrgao(orgaos, destinoId);
				protocoloDto.setOrgaoDestino(orgaoDestino);
				protocoloDto.setLocalizacaoDestino(orgaoDestino.getLocalizacaoPadraoRecebimento());
			}

			if (dp.getTipoDestino().equals(TipoDestino.SETOR)) {
				LocalizacaoBasicDto localizacaoDestino = getLocalizacao(localizacoes, destinoId);
				OrgaoPaeBasicDto orgaoDestino = getOrgao(orgaos, localizacaoDestino.getUnidade().getOrgao().getId());
				protocoloDto.setOrgaoDestino(orgaoDestino);
				protocoloDto.setLocalizacaoDestino(localizacaoDestino);
			}

			ProtocoloUtil.substituirCamposDinamicos(protocoloDto);

			protocoloProducerMessageService.enviarParaFilaProtocolarDocumento(protocoloDto, correlationId.toString());
		}
	}

	private void gerarNumeroDoDocumento(ProtocolarDocumentoDto dto, DocumentoProtocolado dp) {
		if (dto.getNumeroReservadoId() == null) {
			// essa consulta executa em uma transacao independente
			SequencialDocumento sequencial = sequencialDocumentoService.buscarProximoSequencial(dp.getEspecieId(),
					dp.getLocalizacaoOrigemId());
			dp.setAnoDocumento(sequencial.getAno());
			dp.setNumeroDocumento(sequencial.getSequencial());
		} else {
			NumeroDocumentoReservado numeroReservado = sequencialRepository.buscarNumeroReservado(dto.getNumeroReservadoId());
			if (numeroReservado.getDocumentoProtocolado() != null) {
				throw new DomainException("Esse número de documento já foi protocolado");
			}
			dp.setAnoDocumento(numeroReservado.getAno());
			dp.setNumeroDocumento(numeroReservado.getSequencial());
		}
	}

	private List<LocalizacaoBasicDto> getLocalizacoes(List<LocalizacaoBasicDto> localizacoes, List<Long> ids) {
		return localizacoes.stream().filter(a -> ids.contains(a.getId())).collect(Collectors.toList());
	}

	private OrgaoPaeBasicDto getOrgao(List<OrgaoPaeBasicDto> orgaos, Long id) {
		return orgaos.stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
	}

	public LocalizacaoBasicDto getLocalizacao(List<LocalizacaoBasicDto> localizacoes, Long id) {
		return localizacoes.stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
	}

	public List<OrgaoPaeBasicDto> getOrgaos(List<OrgaoPaeBasicDto> orgaos, List<Long> ids) {
		return orgaos.stream().filter(a -> ids.contains(a.getId())).collect(Collectors.toList());
	}

}
