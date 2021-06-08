package br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity;
// Generated 18 de mar de 2021 00:52:35 by Hibernate Tools 5.2.12.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Interessado generated by hbm2java
 */
@Entity
@Table(name = "interessado", schema = "pae")
public class InteressadoEntity implements java.io.Serializable {

	private Long id;
	private ProtocoloEntity protocolo;
	private Long pessoaId;
	private String nome;
	private String tipoInteressado;
	private String sigla;
	private Long setorId;

	public InteressadoEntity() {
	}

	public InteressadoEntity(Long id) {
		this.id = id;
	}

	public InteressadoEntity(Long id, ProtocoloEntity protocolo, Long pessoaId, String nome, String tipoInteressado, String sigla,
			Long setorId) {
		this.id = id;
		this.protocolo = protocolo;
		this.pessoaId = pessoaId;
		this.nome = nome;
		this.tipoInteressado = tipoInteressado;
		this.sigla = sigla;
		this.setorId = setorId;
	}

	@Id
	@SequenceGenerator(name="INTERESSADO_ID_GENERATOR", sequenceName="pae.sq_interessado", allocationSize=1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INTERESSADO_ID_GENERATOR")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "ano", referencedColumnName = "ano"),
			@JoinColumn(name = "numero_protocolo", referencedColumnName = "numero_protocolo") })
	public ProtocoloEntity getProtocolo() {
		return this.protocolo;
	}

	public void setProtocolo(ProtocoloEntity protocolo) {
		this.protocolo = protocolo;
	}

	@Column(name = "pessoa_id")
	public Long getPessoaId() {
		return this.pessoaId;
	}

	public void setPessoaId(Long pessoaId) {
		this.pessoaId = pessoaId;
	}

	@Column(name = "nome")
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "tipo_interessado", length = 20)
	public String getTipoInteressado() {
		return this.tipoInteressado;
	}

	public void setTipoInteressado(String tipoInteressado) {
		this.tipoInteressado = tipoInteressado;
	}

	@Column(name = "sigla", length = 100)
	public String getSigla() {
		return this.sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	@Column(name = "setor_id")
	public Long getSetorId() {
		return this.setorId;
	}

	public void setSetorId(Long setorId) {
		this.setorId = setorId;
	}

}