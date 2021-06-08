package br.gov.pa.prodepa.pae.protocolo.domain.model;
// Generated 11 de mar de 2021 14:36:31 by Hibernate Tools 5.4.27.Final

import java.util.Date;
import java.util.List;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocolarDocumentoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DocumentoProtocolado {

	private Long id;
	private Long especieId;
	private Long assuntoId;
	private Long municipioId;
	private Long documentoId;
	
	private Integer ano;
	private Long sequencial;
	private OrigemDocumento origemDocumento;
	private String complemento;
	private Date dataCadastro;
	private Prioridade prioridade;
	private TipoDestino tipoDestino;
	private List<Long> usuariosQueDevemAssinar;
	private List<Long> usuariosQueJaAssinaram;

	//private List<Long> orgaosDestinoIds;
	//private List<Long> localizacoesDestinoIds;
	
	private List<Long> destinosIds;

	private String conteudoDocumento;
	private Long modeloConteudoId;
	private List<Long> pessoasFisicasInteressadasIds;
	private List<Long> pessoasJuridicasInteressadasIds;
	private List<Long> orgaosInteressadosIds;
	private List<Long> localizacoesInteressadasIds;
	private Long localizacaoOrigemId;
	private Long orgaoOrigemId;
	
	private Boolean jaFoiTramitado;
	
	public static DocumentoProtocolado buildFrom(ProtocolarDocumentoDto dto) {
		return DocumentoProtocolado.builder()
		.especieId(dto.getEspecieId())
		.assuntoId(dto.getAssuntoId())
		.municipioId(dto.getMunicipioId())
		.documentoId(dto.getDocumentoId())
		.origemDocumento(dto.getOrigemDocumento())
		.complemento(dto.getComplemento())
		.prioridade(dto.getPrioridade())
		.tipoDestino(dto.getTipoDestino())
		.usuariosQueDevemAssinar(dto.getAssinantesIds())
		.destinosIds(dto.getDestinosIds())
		/*
		.orgaosDestinoIds(dto.getTipoDestino().equals(TipoDestino.ORGAO) && dto.getDestinosIds() != null
				? dto.getDestinosIds() 
				: null)
		.localizacoesDestinoIds(dto.getTipoDestino().equals(TipoDestino.SETOR)  && dto.getDestinosIds() != null
				? dto.getDestinosIds() 
				: null)
		*/
		.pessoasFisicasInteressadasIds(dto.getInteressadosPessoasFisicasIds())
		.pessoasJuridicasInteressadasIds(dto.getInteressadosPessoasJuridicasIds())
		.orgaosInteressadosIds(dto.getInteressadosOrgaosIds())
		.localizacoesInteressadasIds(dto.getInteressadosLocalizacoesIds())
		.localizacaoOrigemId(dto.getLocalizacaoOrigemId())
		.orgaoOrigemId(dto.getOrgaoOrigemId())
		.build();
	}
	
}
