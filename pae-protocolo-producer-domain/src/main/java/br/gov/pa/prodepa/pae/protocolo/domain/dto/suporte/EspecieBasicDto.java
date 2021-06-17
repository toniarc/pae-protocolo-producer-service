package br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class EspecieBasicDto {

	private Long id;
	private String nome;
	private Boolean geraProtocolo;
	private Integer quantidadeDestinos;	
	private Boolean ativo;
	
	public EspecieBasicDto(){
	}
	
	public EspecieBasicDto(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EspecieBasicDto other = (EspecieBasicDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
