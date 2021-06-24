package br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity;
// Generated 11 de mar de 2021 14:58:24 by Hibernate Tools 5.0.6.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "numero_documento_reservado", schema = "pae")
public class NumeroDocumentoReservadoEntity implements java.io.Serializable {

	private static final long serialVersionUID = 5259946194628960296L;
	
	@Id
	@SequenceGenerator(name="RESERVA_ID_GENERATOR", sequenceName="pae.sq_numero_documento_reservado", allocationSize=1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESERVA_ID_GENERATOR")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "documento_protocolado_id")
	private DocumentoProtocoladoEntity documentoProtocolado;
	
	private Integer ano;
	private Long sequencial;
	
	@ManyToOne
	@JoinColumn(name = "especie_id", nullable = false, insertable = false, updatable = false)
	private EspecieEntity especie;
	
	@Column(name = "especie_id")
	private Long especieId;
	
	@ManyToOne
	@JoinColumn(name = "localizacao_id", nullable = false, insertable = false, updatable = false)
	private LocalizacaoEntity localizacao;
	
	@Column(name = "localizacao_id")
	private Long localizacaoId;
	
	@Column(name = "manut_usuario_id", nullable = false)
	private Long manutUsuarioId;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "manut_data", nullable = false, length = 13)
	private Date manutData;

	public NumeroDocumentoReservadoEntity() {
	}

}
