package br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.MunicipioBasicDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoVO {

	private String logradouro;
	private String numero;
	private String bairro;
	private String cep;
	private String complemento;
	private MunicipioBasicDto municipio;

	public String formatar(){
		if(complemento != null && complemento.trim().length() > 0) {
			return logradouro + ", " + numero  + ", " + complemento  + ", " + bairro + ", " + municipio.getDescricao() + "/"+ municipio.getEstado().getUf()  + ", " +  cep;
		} 
		return logradouro + ", " + numero  + ", " + bairro + ", " + municipio.getDescricao()  + "/"+ municipio.getEstado().getUf() + ", " +  cep;
	}
}