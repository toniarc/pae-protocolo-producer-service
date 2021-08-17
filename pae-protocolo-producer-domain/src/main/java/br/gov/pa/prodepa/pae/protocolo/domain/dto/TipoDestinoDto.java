package br.gov.pa.prodepa.pae.protocolo.domain.dto;

import br.gov.pa.prodepa.pae.protocolo.domain.model.TipoDestino;
import lombok.Data;

@Data
public class TipoDestinoDto {

	private String chave;
	private String valor;
	
	public TipoDestinoDto(TipoDestino tipoDestino) {
		this.chave = tipoDestino.name();
		this.valor = tipoDestino.getDescricao();
	}
}
