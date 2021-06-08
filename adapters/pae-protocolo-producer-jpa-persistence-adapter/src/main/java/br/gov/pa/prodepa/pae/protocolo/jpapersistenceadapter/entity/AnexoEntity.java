package br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity;
// Generated 18 de mar de 2021 00:52:35 by Hibernate Tools 5.2.12.Final

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Anexo generated by hbm2java
 */
@Entity
@Table(name = "anexo", schema = "pae")
public class AnexoEntity implements java.io.Serializable {

	private Integer id;
	private ProtocoloEntity protocolo;
	private Boolean assinadoPorTodos;
	private Integer quantidadeAssinaturas;
	private String conteudo;
	private Date anexadoEm;
	private String s3StorageId;
	private Float tamanhoArquivoMb;
	private Integer quantidadePaginas;
	private Boolean documentoInicial;
	private Long sequencial;
	private String hashArquivo;
	private String hashAlgoritmo;
	private Set<AssinaturaEntity> assinaturas;

	public AnexoEntity() {
	}

	@Id
	@SequenceGenerator(name="ANEXO_ID_GENERATOR", sequenceName="pae.sq_anexo", allocationSize=1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ANEXO_ID_GENERATOR")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "ano_protocolo", referencedColumnName = "ano", nullable = false),
			@JoinColumn(name = "numero_protocolo", referencedColumnName = "numero_protocolo", nullable = false) })
	public ProtocoloEntity getProtocolo() {
		return this.protocolo;
	}

	public void setProtocolo(ProtocoloEntity protocolo) {
		this.protocolo = protocolo;
	}

	@Column(name = "assinado_por_todos", nullable = false)
	public Boolean isAssinadoPorTodos() {
		return this.assinadoPorTodos;
	}

	public void setAssinadoPorTodos(Boolean assinadoPorTodos) {
		this.assinadoPorTodos = assinadoPorTodos;
	}

	@Column(name = "quantidade_assinaturas", nullable = false)
	public Integer getQuantidadeAssinaturas() {
		return this.quantidadeAssinaturas;
	}

	public void setQuantidadeAssinaturas(Integer quantidadeAssinaturas) {
		this.quantidadeAssinaturas = quantidadeAssinaturas;
	}

	@Column(name = "conteudo")
	public String getConteudo() {
		return this.conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "anexado_em", nullable = false, length = 13)
	public Date getAnexadoEm() {
		return this.anexadoEm;
	}

	public void setAnexadoEm(Date anexadoEm) {
		this.anexadoEm = anexadoEm;
	}

	@Column(name = "s3_storage_id", nullable = false, length = 100)
	public String getS3StorageId() {
		return this.s3StorageId;
	}

	public void setS3StorageId(String s3StorageId) {
		this.s3StorageId = s3StorageId;
	}

	@Column(name = "tamanho_arquivo_mb", nullable = false)
	public Float getTamanhoArquivoMb() {
		return this.tamanhoArquivoMb;
	}

	public void setTamanhoArquivoMb(Float tamanhoArquivoMb) {
		this.tamanhoArquivoMb = tamanhoArquivoMb;
	}

	@Column(name = "quantidade_paginas", nullable = false)
	public Integer getQuantidadePaginas() {
		return this.quantidadePaginas;
	}

	public void setQuantidadePaginas(Integer quantidadePaginas) {
		this.quantidadePaginas = quantidadePaginas;
	}

	@Column(name = "documento_inicial", nullable = false)
	public Boolean isDocumentoInicial() {
		return this.documentoInicial;
	}

	public void setDocumentoInicial(Boolean documentoInicial) {
		this.documentoInicial = documentoInicial;
	}

	@Column(name = "sequencial", nullable = false)
	public Long getSequencial() {
		return this.sequencial;
	}

	public void setSequencial(Long sequencial) {
		this.sequencial = sequencial;
	}

	@Column(name = "hash_arquivo", nullable = false, length = 200)
	public String getHashArquivo() {
		return this.hashArquivo;
	}

	public void setHashArquivo(String hashArquivo) {
		this.hashArquivo = hashArquivo;
	}

	@Column(name = "hash_algoritmo", length = 10)
	public String getHashAlgoritmo() {
		return this.hashAlgoritmo;
	}

	public void setHashAlgoritmo(String hashAlgoritmo) {
		this.hashAlgoritmo = hashAlgoritmo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "anexo", cascade = CascadeType.ALL)
	public Set<AssinaturaEntity> getAssinaturas() {
		return this.assinaturas;
	}

	public void setAssinaturas(Set<AssinaturaEntity> assinaturas) {
		this.assinaturas = assinaturas;
	}

}