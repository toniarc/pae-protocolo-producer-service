package br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte;

public enum TipoEntradaSaidaProcesso {
	QUALQUER_SETOR("Qualquer Setor"),
	SOMENTE_SETOR_DE_PROTOCOLO("Somente setor de protocolo");
	
	private String descricao;
	
	private TipoEntradaSaidaProcesso(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}

