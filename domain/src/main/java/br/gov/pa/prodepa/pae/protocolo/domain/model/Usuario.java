package br.gov.pa.prodepa.pae.protocolo.domain.model;

import br.gov.pa.prodepa.pae.suporte.client.OrgaoPaeBasicDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

	private Long id;
	private String nome;
	private OrgaoPaeBasicDto orgao;
	
}
