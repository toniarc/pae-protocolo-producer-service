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
	private List<Long> assinantesIds;
	private List<Long> destinoIds;
	private String conteudoDocumento;
	private Long modeloConteudoId;
	private List<Long> pessoaFisicaInteressadoIds;
	private List<Long> pessoaJuridicaInteressadoIds;
	private List<Long> orgaoInteressadoIds;
	private List<Long> localizacaoInteressadoIds;
	private Long localizacaoOrigemId;
	private Long orgaoOrigemId;
	
	public static DocumentoProtocolado buildFrom(ProtocolarDocumentoDto dto) {
		
		DocumentoProtocolado dp = DocumentoProtocolado.builder()
		.especieId(dto.getEspecieId())
		.assuntoId(dto.getAssuntoId())
		.municipioId(dto.getMunicipioId())
		.documentoId(dto.getDocumentoId())
		.origemDocumento(dto.getOrigemDocumento())
		.complemento(dto.getComplemento())
		.prioridade(dto.getPrioridade())
		.tipoDestino(dto.getTipoDestino())
		.assinantesIds(dto.getAssinantesIds())
		.destinoIds(dto.getDestinosIds())
		.pessoaFisicaInteressadoIds(dto.getInteressadosPessoasFisicasIds())
		.pessoaJuridicaInteressadoIds(dto.getInteressadosPessoasFisicasIds())
		.orgaoInteressadoIds(dto.getInteressadosOrgaosIds())
		.localizacaoInteressadoIds(dto.getInteressadosLocalizacoesIds())
		.localizacaoOrigemId(dto.getLocalizacaoOrigemId())
		.orgaoOrigemId(dto.getOrgaoOrigemId())
		.build();
		
		return dp;
	}
	
}
