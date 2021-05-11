package br.gov.pa.prodepa.pae.protocolo.domain.dto;

import br.gov.pa.prodepa.pae.protocolo.domain.model.TipoInteressado;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InteressadoSearchDto {

	private TipoInteressado tipoInteressado;
	private String nome;
	private String sigla;
	private String cpfCnpj;
	private int pageSize;
	private int pageNumber;
	
}
