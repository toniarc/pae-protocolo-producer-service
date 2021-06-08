package br.gov.pa.prodepa.pae.protocolo.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SequencialProtolo {

	private Integer ano;
	private Long sequencial;
	public SequencialProtolo(Integer ano, Long sequencial) {
		super();
		this.ano = ano;
		this.sequencial = sequencial;
	}
	
}
