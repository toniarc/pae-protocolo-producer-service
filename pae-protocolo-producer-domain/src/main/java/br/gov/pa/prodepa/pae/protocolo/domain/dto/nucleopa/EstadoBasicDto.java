package br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoBasicDto {

	private Long id;
	private String descricao;
	private String uf;
	private String codigoIbge;
	
	public EstadoBasicDto() {
	}

	public EstadoBasicDto(Long id, String descricao, String uf, String codigoIbge) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.uf = uf;
		this.codigoIbge = codigoIbge;
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
		EstadoBasicDto other = (EstadoBasicDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
