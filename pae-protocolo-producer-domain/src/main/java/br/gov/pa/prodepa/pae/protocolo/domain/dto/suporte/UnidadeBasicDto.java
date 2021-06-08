package br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnidadeBasicDto {
	
	private Long id;
	private String nome;
	private String endereco;

	public UnidadeBasicDto(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
}

