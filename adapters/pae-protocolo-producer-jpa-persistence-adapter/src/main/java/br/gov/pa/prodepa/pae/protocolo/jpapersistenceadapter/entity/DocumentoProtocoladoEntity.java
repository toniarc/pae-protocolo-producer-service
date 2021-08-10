package br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity;
// Generated 11 de mar de 2021 14:58:24 by Hibernate Tools 5.0.6.Final

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.array.ListArrayType;

import br.gov.pa.prodepa.pae.protocolo.domain.model.OrigemDocumento;
import br.gov.pa.prodepa.pae.protocolo.domain.model.Prioridade;
import br.gov.pa.prodepa.pae.protocolo.domain.model.TipoDestino;
import lombok.Getter;
import lombok.Setter;

/**
 * DocumentoProtocolado generated by hbm2java
 */

@Getter
@Setter
@TypeDef(
	    name = "list-array",
	    typeClass = ListArrayType.class
)
@Entity
@Table(name = "documento_protocolado", schema = "pae")
public class DocumentoProtocoladoEntity implements Serializable {

	private static final long serialVersionUID = 8498989069534374001L;

	@Id
	@SequenceGenerator(name="DOCUMENTO_PROTOCOLADO_ID_GENERATOR", sequenceName="pae.sq_documento_protocolado", allocationSize=1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DOCUMENTO_PROTOCOLADO_ID_GENERATOR")
	private Long id;
	
	@Column(name = "especie_id", nullable = false)
	private Long especieId;
	
	@Column(name = "assunto_id", nullable = false)
	private Long assuntoId;
	
	@Column(name = "municipio_ibge", nullable = false)
	private String municipioIbge;
	
	@Column(name = "documento_id")
	private Long documentoId;

	@Column(name = "ano_documento")
	private Integer anoDocumento;

	@Column(name = "numero_documento")
	private Long numeroDocumento;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "origem_documento", nullable = false, length = 20)
	private OrigemDocumento origemDocumento;
	
	private String complemento;
	
	@Enumerated(EnumType.STRING)
	private Prioridade prioridade;
	
	@Enumerated(EnumType.STRING)
	private TipoDestino tipoDestino;
	
	@Type(type = "list-array")
	@Column(name = "usuarios_que_devem_assinar", nullable = false)
	private List<Long> usuariosQueDevemAssinar;
	
	@Type(type = "list-array")
	@Column(name = "usuarios_que_ja_assinaram", nullable = false)
	private List<Long> usuariosQueJaAssinaram;
	
	@Type(type = "list-array")
	@Column(name = "destino_ids", nullable = false)
	private List<Long> destinosIds;
	
	@Column(name = "conteudo_documento", nullable = false)
	private String conteudoDocumento;
	
	@Column(name = "modelo_conteudo_id", nullable = false)
	private String modeloConteudoId;
	
	@Type(type = "list-array")
	@Column(name = "pessoa_fisica_interessado_ids")
	private List<Long> pessoasFisicasInteressadasIds;
	
	@Type(type = "list-array")
	@Column(name = "pessoa_juridica_interessado_ids")
	private List<Long> pessoasJuridicasInteressadasIds;
	
	@Type(type = "list-array")
	@Column(name = "orgao_interessado_ids")
	private List<Long> orgaosInteressadosIds;
	
	@Type(type = "list-array")
	@Column(name = "localizacao_interessado_ids")
	private List<Long> localizacoesInteressadasIds;
	
	@Column(name = "localizacao_origem_id")
	private Long localizacaoOrigemId;
	
	@Column(name = "orgao_origem_id")
	private Long orgaoOrigemId;
	
	@Column(name = "ja_foi_tramitado")
	private Boolean jaFoiTramitado;

	@Column(name = "criado_por")
	private Long criadoPor;

	@Column(name = "criado_em")
	private Date criadoEm;

	@Column(name = "atualizado_por")
	private Long atualizadoPor;

	@Column(name = "atualizado_em")
	private Date atualizadoEm;

	public DocumentoProtocoladoEntity() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentoProtocoladoEntity other = (DocumentoProtocoladoEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}
