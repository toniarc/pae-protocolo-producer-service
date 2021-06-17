package br.gov.pa.prodepa.pae.protocolo.domain.validator;

import java.util.List;

import br.gov.pa.prodepa.pae.common.domain.dto.UsuarioDto;
import br.gov.pa.prodepa.pae.common.domain.exception.DomainException;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocolarDocumentoDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.AssuntoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.EspecieBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.LocalizacaoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.OrgaoPaeBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.model.TipoDestino;

public class ProtocoloValidator {

	private static final String USUARIO_SEM_LOCALIZACAO = "Usuário deve estar vinculado a pelo menos um setor no órgão em que está logado. Entre em contato como administrador do Sistema";

	private static final String CAMPO_COMPLEMENTO_OBRIGATORIO = null;

	private static final String CAMPO_PRIORIDADE_OBRIGATORIO = null;

	private static final String CAMPO_INTERESSADO_OBRIGATORIO = "Para protocolar um documento deve ser informado pelo menos um interessado";

	private static final String CAMPO_ASSINANTE_OBRIGATORIO = "Para protocolar um documento deve ser informado pelo menos um assinante";

	private static final String CAMPO_ORGAO_ORIGEM_OBRIGATORIO = null;

	private static final String CAMPO_TIPO_DESTINO_OBRIGATORIO = null;

	private static final String CAMPO_DESTINO_OBRIGATORIO = null;

	private static final String CAMPO_ASSUNTO_OBRIGATORIO = null;

	private static final String CAMPO_ESPECIE_OBRIGATORIO = null;

	private static final String CAMPO_DOCUMENTO_OBRIGATORIO = null;

	private static final String CAMPO_LOCALIZACAO_ORIGEM_OBRIGATORIO = null;

	private static final String CAMPO_MUNICIPIO_OBRIGATORIO = null;

	private static final String CAMPO_ORIGEM_DOCUMENTO_OBRIGATORIO = null;

	private static final String ESPECIE_NAO_GERA_PROTOCOLO = "A espécie do documento está configurada para não gerar protocolo. Entre em contato com o gestor do sistema.";

	private static final String ESPECIE_ULTRAPASSOU_QUANTIDADE_MAXIMA_DESTINOS = "A espécie selecionada só permite uma quantidade máxima de %d destino(s)";

	private static final String SETOR_SEM_RESPONSAVEL = "O setor %s não possui um responsável";

	private static final String SETOR_DESTINO_IGUAL_ORIGEM = "O setor de destino não pode ser igual ao setor de origem";

	private static final String ORGAO_SETOR_DESTINO_DIFERENTE_ORGAO_USUARIO = "O setor %s não pertence ao órgao do usuário logado";

	DomainException de = new DomainException();
	private ProtocolarDocumentoDto dto;
	
	public ProtocoloValidator(ProtocolarDocumentoDto dto) {
		this.dto = dto;
	}

	public static ProtocoloValidator getInstance(ProtocolarDocumentoDto dto) {
		return new ProtocoloValidator(dto);
	}

	public ProtocoloValidator validar() {
		de.throwException();
		return this;
	}

	/*
	public ProtocoloValidator validarCamposDinamicosUsadosNoDocumento(String conteudo, TipoDestino tipoDestino) {
		
		Pattern pattern = Pattern.compile("(%24%7B([A-Z_]*)%7D)");
		Matcher matcher = pattern.matcher(conteudo);
		
		while(matcher.find()) {
			String campoDinamico = matcher.group(2);
			
			if(!campoDinamico.equals("DATA_PROTOCOLO") && !campoDinamico.equals("NUMERO_PROTOCOLO")) {
				
				if(!tipoDestino.equals(TipoDestino.ORGAO)) {
					
					if(campoDinamico.trim().equals("ORGAO_ORIGEM_NOME") ) {
						de.addError("O campo dinâmico 'ORGAO_ORIGEM_NOME' só pode ser usado quando o tipo de destino for 'ÓRGÃO'");
					}

					if(campoDinamico.trim().equals("ORGAO_DESTINO_SIGLA") ) {
						de.addError("O campo dinâmico 'ORGAO_DESTINO_SIGLA' só pode ser usado quando o tipo de destino for 'ÓRGÃO'");
					}
					
					if(campoDinamico.trim().equals("ORGAO_DESTINO_NOME") ) {
						de.addError("O campo dinâmico 'ORGAO_DESTINO_NOME' só pode ser usado quando o tipo de destino for 'ÓRGÃO'");
					}
				}
				
				if(!tipoDestino.equals(TipoDestino.SETOR)) {
					if(campoDinamico.trim().equals("SETOR_DESTINO_SIGLA") ) {
						de.addError("O campo dinâmico 'SETOR_DESTINO_SIGLA' só pode ser usado quando o tipo de destino for 'SETOR'");
					}
					
					if(campoDinamico.trim().equals("SETOR_DESTINO_DESCRICAO") ) {
						de.addError("O campo dinâmico 'SETOR_DESTINO_DESCRICAO' só pode ser usado quando o tipo de destino for 'SETOR'");
					}
					if(campoDinamico.trim().equals("SETOR_DESTINO_ENDERECO") ) {
						de.addError("O campo dinâmico 'SETOR_DESTINO_ENDERECO' só pode ser usado quando o tipo de destino for 'SETOR'");
					}
				}
				
			}
			
		}
		return this;
	}*/
	
	public ProtocoloValidator validarSeAEspecieExiste(EspecieBasicDto especie) {
		if(especie == null || especie.getId() == null) {
			de.addError("A espécie selecionada não existe [id: " + dto.getEspecieId() + "]" );
		}
		return this;
	}

	public ProtocoloValidator validarSeOAssuntoExiste(AssuntoBasicDto assunto) {
		if(assunto == null || assunto.getId() == null) {
			de.addError("O assunto selecionado não existe [id: " + dto.getAssuntoId() + "]" );
		}
		return this;
	}

	public ProtocoloValidator validarSeOOrgaoInformadoComoInteressadoExiste(List<OrgaoPaeBasicDto> orgaos) {
		for(Long id : dto.getInteressadosOrgaosIds()) {
			if(!orgaos.contains(OrgaoPaeBasicDto.builder().id(id).build())) {
				de.addError("O órgão interessado selecionado não existe [id: " + id + "]" );
			}
		}
		return this;
	}
	
	public ProtocoloValidator validarSeOrgaoOrigemInformadoExiste(List<OrgaoPaeBasicDto> orgaos) {
		if(!orgaos.contains(OrgaoPaeBasicDto.builder().id(dto.getOrgaoOrigemId()).build())) {
			de.addError("O órgão origem selecionado não existe [id: " + dto.getOrgaoOrigemId() + "]" );
		}
		
		return this;
	}
	
	public ProtocoloValidator validarSeOsDestinosInformadosExistem(List<OrgaoPaeBasicDto> orgaos, List<LocalizacaoBasicDto> localizacoes) {
		if(dto.getTipoDestino().equals(TipoDestino.ORGAO)) {
			for(Long id : dto.getDestinosIds()) {
				if(!orgaos.contains(OrgaoPaeBasicDto.builder().id(id).build())) {
					de.addError("O órgão destino selecionado não existe [id: " + id + "]" );
				}
			}
		} else {
			for(Long id : dto.getDestinosIds()) {
				if(!localizacoes.contains(LocalizacaoBasicDto.builder().id(id).build())) {
					de.addError("A localização destino selecionado não existe [id: " + id + "]" );
				}
			}
		}
		return this;
	}
	
	public ProtocoloValidator validarSeAsLocalizacoesInteressadasExistem(List<LocalizacaoBasicDto> localizacoes) {
		for(Long id : dto.getInteressadosLocalizacoesIds()) {
			if(!localizacoes.contains(LocalizacaoBasicDto.builder().id(id).build())) {
				de.addError("A localização interessada selecionada não existe [id: " + id + "]" );
			}
		}
		return this;
	}
	
	public ProtocoloValidator validarSeALocalizacaoOrigemInformadaExiste(List<LocalizacaoBasicDto> localizacoes) {
		
		if(!localizacoes.contains(LocalizacaoBasicDto.builder().id(dto.getLocalizacaoOrigemId()).build())) {
			de.addError("A localização origem selecionada não existe [id: " + dto.getLocalizacaoOrigemId() + "]" );
		}
	
		return this;
	}

	public ProtocoloValidator validarSeTodosOsOrgaoDestinosPossuemSetorPadraoComResponsavel(List<OrgaoPaeBasicDto> orgaos) {
		if(dto.getTipoDestino().equals(TipoDestino.ORGAO) && orgaos != null && !orgaos.isEmpty()) {

			orgaos.stream()
				.filter( o -> dto.getDestinosIds().contains(o.getId()))
				.forEach( o -> {
					if(o.getLocalizacaoPadraoRecebimento() == null) {
						de.addError(String.format("O órgão %s não possui uma localização padrão", o.getSigla()));
					} else {
						if(o.getLocalizacaoPadraoRecebimento().getResponsavel() == null) {
							de.addError(String.format("O setor padrão do órgão %s não possui um responsável", o.getSigla()));
						}
					}
				});
		}
		return this;
	}

    public ProtocoloValidator validarSeOUsuarioPossuiAoMenosUmVinculoAtivo(List<LocalizacaoBasicDto> localizacoesUsuario) {
        if( localizacoesUsuario == null || localizacoesUsuario.isEmpty() ){
			de.addError(USUARIO_SEM_LOCALIZACAO);
		} 
		return this;
    }

	public ProtocoloValidator validarCamposObrigatorios() {

		if(dto.getComplemento() == null || dto.getComplemento().isEmpty()){
			de.addError(CAMPO_COMPLEMENTO_OBRIGATORIO);
		}

		if(dto.getPrioridade() == null){
			de.addError(CAMPO_PRIORIDADE_OBRIGATORIO);
		}

		if( (dto.getInteressadosLocalizacoesIds() == null || dto.getInteressadosLocalizacoesIds().isEmpty()) &&
				(dto.getInteressadosOrgaosIds() == null || dto.getInteressadosOrgaosIds().isEmpty()) &&
				(dto.getInteressadosPessoasFisicasIds() == null || dto.getInteressadosPessoasFisicasIds().isEmpty()) &&
				(dto.getInteressadosPessoasJuridicasIds() == null || dto.getInteressadosPessoasJuridicasIds().isEmpty())
				) {
			de.addError(CAMPO_INTERESSADO_OBRIGATORIO);
		}

		if(dto.getAssinantesIds() == null || dto.getAssinantesIds().isEmpty()){
			de.addError(CAMPO_ASSINANTE_OBRIGATORIO);
		}

		if(dto.getOrgaoOrigemId() == null){
			de.addError(CAMPO_ORGAO_ORIGEM_OBRIGATORIO);
		}

		if(dto.getTipoDestino() == null){
			de.addError(CAMPO_TIPO_DESTINO_OBRIGATORIO);
		}

		if(dto.getDestinosIds() == null || dto.getDestinosIds().isEmpty()){
			de.addError(CAMPO_DESTINO_OBRIGATORIO);
		}

		if(dto.getAssuntoId() == null ){
			de.addError(CAMPO_ASSUNTO_OBRIGATORIO);
		}

		if(dto.getEspecieId() == null ){
			de.addError(CAMPO_ESPECIE_OBRIGATORIO);
		}

		if(dto.getDocumentoId()== null){
			de.addError(CAMPO_DOCUMENTO_OBRIGATORIO);
		}

		if(dto.getLocalizacaoOrigemId()== null){
			de.addError(CAMPO_LOCALIZACAO_ORIGEM_OBRIGATORIO);
		}

		if(dto.getMunicipioIbge() == null){
			de.addError(CAMPO_MUNICIPIO_OBRIGATORIO);
		}

		if(dto.getOrigemDocumento() == null){
			de.addError(CAMPO_ORIGEM_DOCUMENTO_OBRIGATORIO);
		}

		return this;
	}

    public ProtocoloValidator verificarSeAEspeciePodeGerarProtocolo(EspecieBasicDto especie) {
		if(especie.getGeraProtocolo() == null || !especie.getGeraProtocolo()){
			de.addError(ESPECIE_NAO_GERA_PROTOCOLO);
		}
        return this;
    }

	public ProtocoloValidator validarAQuantidadeDeDestinosQueAEspeciePermite(EspecieBasicDto especie) {
		if(especie.getQuantidadeDestinos() < dto.getDestinosIds().size()){
			de.addError(String.format(ESPECIE_ULTRAPASSOU_QUANTIDADE_MAXIMA_DESTINOS, especie.getQuantidadeDestinos()));
		}
		return this;
	}

    public ProtocoloValidator validarSeTodosOsSetoresDeDestinoSaoDoMesmoOrgaoDoUsuarioLogado(List<LocalizacaoBasicDto> localizacoes, UsuarioDto usuarioLogado) {
		if(dto.getTipoDestino().equals(TipoDestino.SETOR)){
			localizacoes.stream()
			.filter(l -> dto.getDestinosIds().contains(l.getId()))
			.forEach( l -> {
				if(!l.getUnidade().getOrgao().getId().equals(usuarioLogado.getOrgao().getId())){
					de.addError(String.format(ORGAO_SETOR_DESTINO_DIFERENTE_ORGAO_USUARIO, l.getSetor().getNome()));
				}
			});
		}
        return this;
    }

	public ProtocoloValidator validarSeTodosOsSetoresDeDestinoPossuemResponsavel(List<LocalizacaoBasicDto> localizacoes) {
		if(dto.getTipoDestino().equals(TipoDestino.SETOR)){
			localizacoes.stream()
				.filter(l -> dto.getDestinosIds().contains(l.getId()))
				.forEach( l -> {
					if(l.getResponsavel() == null || l.getResponsavel().getId() == null){
						de.addError(String.format(SETOR_SEM_RESPONSAVEL, l.getSetor().getNome()));
					}
				});
		}
		return this;
	}

	public ProtocoloValidator validarSeAlgumaLocalizacaoDeDestinoEhIgualALocalizacaoDeOrigem(List<LocalizacaoBasicDto> localizacoes) {
		if(dto.getTipoDestino().equals(TipoDestino.SETOR)){
			localizacoes.stream()
				.filter(l -> dto.getDestinosIds().contains(l.getId()))
				.forEach( l -> {
					if(l.getId().equals(dto.getLocalizacaoOrigemId())){
						de.addError(SETOR_DESTINO_IGUAL_ORIGEM);
					}
				});
		}
		return this;
	}

}
