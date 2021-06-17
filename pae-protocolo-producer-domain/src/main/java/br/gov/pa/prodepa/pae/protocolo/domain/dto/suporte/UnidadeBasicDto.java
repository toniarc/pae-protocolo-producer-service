package br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnidadeBasicDto {
	
	private Long id;
	private String nome;
	private EnderecoVO enderecoVO;
	private OrgaoPaeBasicDto orgao;

	public UnidadeBasicDto(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
}

