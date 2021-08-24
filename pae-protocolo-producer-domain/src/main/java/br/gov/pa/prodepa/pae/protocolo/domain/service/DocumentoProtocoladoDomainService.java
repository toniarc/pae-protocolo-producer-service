package br.gov.pa.prodepa.pae.protocolo.domain.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.gov.pa.prodepa.pae.common.domain.dto.UsuarioDto;
import br.gov.pa.prodepa.pae.common.domain.exception.DomainException;
import br.gov.pa.prodepa.pae.protocolo.domain.ProtocoloCache;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.DestinoProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.DocumentoProtocoladoFullDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocolarDocumentoDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.documento.DocumentoProtocoladoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.LocalizacaoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.OrgaoPaeBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.model.DocumentoProtocolado;
import br.gov.pa.prodepa.pae.protocolo.domain.model.NumeroDocumentoReservado;
import br.gov.pa.prodepa.pae.protocolo.domain.model.SequencialDocumento;
import br.gov.pa.prodepa.pae.protocolo.domain.model.TipoDestino;
import br.gov.pa.prodepa.pae.protocolo.domain.port.ControleAcessoService;
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
	private final ControleAcessoService controleAcessoService;
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

		Date now = new Date();

		ProtocoloCache cache = new ProtocoloCache(suporteSevice, documentoService, controleAcessoService, dto, usuarioLogado);

		validator
			.verificarSeAEspeciePodeGerarProtocolo(cache.getEspecie())
			.validarAQuantidadeDeDestinosQueAEspeciePermite(cache.getEspecie())
			.validarSeAEspecieExiste(cache.getEspecie())
			.validarSeOAssuntoExiste(cache.getAssunto())
			.validarSeOOrgaoInformadoComoInteressadoExiste(cache.getOrgaos())
			.validarSeOrgaoOrigemInformadoExiste(cache.getOrgaos())
			.validarSeOsDestinosInformadosExistem(cache.getOrgaos(), cache.getLocalizacoes())
			.validarSeAsLocalizacoesInteressadasExistem(cache.getLocalizacoes())
			.validarSeALocalizacaoOrigemInformadaExiste(cache.getLocalizacoes())
			.validarSeTodosOsOrgaoDestinosPossuemSetorPadraoComResponsavel(cache.getOrgaos())
			.validarSeTodosOsSetoresDeDestinoSaoDoMesmoOrgaoDoUsuarioLogado(cache.getLocalizacoes(), usuarioLogado)
			.validarSeTodosOsSetoresDeDestinoPossuemResponsavel(cache.getLocalizacoes())
			.validarSeAlgumaLocalizacaoDeDestinoEhIgualALocalizacaoDeOrigem(cache.getLocalizacoes())
			.validarSeOUsuarioPossuiAoMenosUmVinculoAtivo(cache.getLocalizacoesUsuario())
		.validar();

		if(dto.getInteressadosPessoasFisicasIds() != null) {
			cache.setPessoasFisicas(nucleopaService.buscarPessoaFisicaPorId(new HashSet<>(dto.getInteressadosPessoasFisicasIds())));
		}
		
		if(dto.getInteressadosPessoasJuridicasIds() != null) {
			cache.setPessoasJuridicas(nucleopaService.buscarPessoaJuridicaPorId(new HashSet<>(dto.getInteressadosPessoasJuridicasIds())));
		}
		
		cache.setMunicipio(nucleopaService.buscarMunicipioPorCodigoIbge(dto.getMunicipioIbge()));

		DocumentoProtocolado dp = DocumentoProtocolado.buildFrom(dto);

		gerarNumeroDoDocumento(dto, dp);

		dp.setConteudoDocumento(cache.getDocumento().getConteudo());
		dp.setModeloConteudoId(cache.getDocumento().getModeloConteudo().getId());
		dp.setJaFoiTramitado(false);

		dp.setCriadoPor(usuarioLogado.getId());
		dp.setCriadoEm(now);
		dp.setAtualizadoPor(usuarioLogado.getId());
		dp.setAtualizadoEm(now);

		DocumentoProtocolado documentoProtocoladoSalvo = documentoRepository.salvar(dp);

		UUID correlationId = UUID.randomUUID();
		
		List<ProtocoloDto> protocolos = new ArrayList<>();
		enviarParaFilaDeProtocolos(dto, cache, documentoProtocoladoSalvo, correlationId.toString(), protocolos);
		enviarParaFilaDaCaixaDeEntrada(cache, documentoProtocoladoSalvo, correlationId.toString(), protocolos);
	}

	private void enviarParaFilaDaCaixaDeEntrada(ProtocoloCache cache, DocumentoProtocolado dp, String correlationId, List<ProtocoloDto> protocolos) {
		DocumentoProtocoladoFullDto dpf = DocumentoProtocoladoFullDto.builder()
		.id(dp.getId())
		.anoDocumento(dp.getAnoDocumento())
		.numeroDocumento(dp.getNumeroDocumento())
		.especie(cache.getEspecie())
		.assunto(cache.getAssunto())
		.municipio(cache.getMunicipio())
		.documento(cache.getDocumento())
		.origemDocumento(dp.getOrigemDocumento())
		.complemento(dp.getComplemento())
		.prioridade(dp.getPrioridade())
		.tipoDestino(dp.getTipoDestino())
		.usuariosQueDevemAssinar(cache.getUsuarios(dp.getUsuariosQueDevemAssinar()))
		.conteudoDocumento(dp.getConteudoDocumento())
		.modeloConteudoId(dp.getModeloConteudoId())
		.pessoasFisicasInteressadas(cache.getPessoasFisicas(dp.getPessoasFisicasInteressadasIds()))
		.pessoasJuridicasInteressadas(cache.getPessoasJuridicas(dp.getPessoasJuridicasInteressadasIds()))
		.orgaosInteressados(cache.getOrgaos(dp.getOrgaosInteressadosIds()))
		.localizacoesInteressadas(cache.getLocalizacoes(dp.getLocalizacoesInteressadasIds()))
		.localizacaoOrigem(cache.getLocalizacao(dp.getLocalizacaoOrigemId()))
		.orgaoOrigem(cache.getOrgao(dp.getOrgaoOrigemId()))
		.jaFoiTramitado(dp.getJaFoiTramitado())
		.criadoEm(dp.getCriadoEm())
		.criadoPor(dp.getCriadoPor())
		.atualizadoEm(dp.getAtualizadoEm())
		.atualizadoPor(dp.getAtualizadoPor())
		.destinos(protocolos.stream().map(p -> DestinoProtocoloDto.builder()
					.orgaoDestino(p.getOrgaoDestino())
					.localizacaoDestino(p.getLocalizacaoDestino())
					.build())
				.collect(Collectors.toList()))
		.build();

		protocoloProducerMessageService.enviarParaFilaDaCaixaDeEntrada(dpf, correlationId);
	}

	private void enviarParaFilaDeProtocolos(ProtocolarDocumentoDto dto, ProtocoloCache cache, DocumentoProtocolado dps, String correlationId, List<ProtocoloDto> protocolos) {

		for (Long destinoId : dps.getDestinosIds()) {
			ProtocoloDto protocoloDto = new ProtocoloDto();
			protocoloDto.setTipoDestino(dps.getTipoDestino());
			protocoloDto.setAssunto(cache.getAssunto());
			protocoloDto.setEspecie(cache.getEspecie());

			protocoloDto.setDocumento(cache.getDocumento());
			protocoloDto.setDocumentoProtocolado(
				DocumentoProtocoladoBasicDto.builder()
					.ano(dps.getAnoDocumento())
					.id(dps.getId())
					.numero(dps.getNumeroDocumento())
					.criadoEm(dps.getCriadoEm())
					.build());

			protocoloDto.setComplemento(dps.getComplemento());
			protocoloDto.setLocalizacaoOrigem(cache.getLocalizacao(dps.getLocalizacaoOrigemId()));
			protocoloDto.setOrgaoOrigem(cache.getOrgao(dto.getOrgaoOrigemId()));
			protocoloDto.setOrigemDocumento(dps.getOrigemDocumento());
			protocoloDto.setPrioridade(dps.getPrioridade());
			protocoloDto.setMunicipio(cache.getMunicipio());
			protocoloDto.setUsuarioCadastro(usuarioLogado);

			if(dps.getPessoasFisicasInteressadasIds() != null) {
				protocoloDto.setInteressadosPessoasFisicas(dps.getPessoasFisicasInteressadasIds().stream().map(cache::getPessoaFisica).collect(Collectors.toList()));
			}
			
			if(dps.getPessoasJuridicasInteressadasIds() != null) {
				protocoloDto.setInteressadosPessoasJuricas(dps.getPessoasJuridicasInteressadasIds().stream().map(cache::getPessoaJuridica).collect(Collectors.toList()));
			}

			if(dps.getOrgaosInteressadosIds() != null) {
				protocoloDto.setOrgaosInteressados(cache.getOrgaos(dps.getOrgaosInteressadosIds()));
			}
			
			if(dps.getLocalizacoesInteressadasIds() != null) {
				protocoloDto.setLocalizacoesInteressadas(cache.getLocalizacoes(dps.getLocalizacoesInteressadasIds()));
			}

			// TODO alterar
			protocoloDto.setAssinantes(dps.getUsuariosQueDevemAssinar().stream().map(id -> UsuarioDto.builder().id(id).build()).collect(Collectors.toList()));

			if (dps.getTipoDestino().equals(TipoDestino.ORGAO)) {
				OrgaoPaeBasicDto orgaoDestino = cache.getOrgao(destinoId);
				protocoloDto.setOrgaoDestino(orgaoDestino);
				protocoloDto.setLocalizacaoDestino(orgaoDestino.getLocalizacaoPadraoRecebimento());
			}

			if (dps.getTipoDestino().equals(TipoDestino.SETOR)) {
				LocalizacaoBasicDto localizacaoDestino = cache.getLocalizacao(destinoId);
				OrgaoPaeBasicDto orgaoDestino = cache.getOrgao(localizacaoDestino.getUnidade().getOrgao().getId());
				protocoloDto.setOrgaoDestino(orgaoDestino);
				protocoloDto.setLocalizacaoDestino(localizacaoDestino);
			}

			ProtocoloUtil.substituirCamposDinamicos(protocoloDto);

			protocoloProducerMessageService.enviarParaFilaProtocolarDocumento(protocoloDto, correlationId);
			
			protocolos.add(protocoloDto);
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

}
