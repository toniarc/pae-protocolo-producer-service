package br.gov.pa.prodepa.pae.protocolo.domain.dto.documento;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public final class Margem {

	private String topo;
	
	private String rodape;
	
	private String direita;
	
	private String esquerda;

	@Builder
	public Margem(String topo, String direita, String rodape, String esquerda) {
		super();
		this.topo = topo;
		this.rodape = rodape;
		this.direita = direita;
		this.esquerda = esquerda;
	}
	
}
