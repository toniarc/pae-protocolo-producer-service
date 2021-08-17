package br.gov.pa.prodepa.pae.protocolo.domain.model;

public enum TipoDestino {
	ORGAO("Órgão"), SETOR("Setor");
	
	private String descricao;
	
	private TipoDestino(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
