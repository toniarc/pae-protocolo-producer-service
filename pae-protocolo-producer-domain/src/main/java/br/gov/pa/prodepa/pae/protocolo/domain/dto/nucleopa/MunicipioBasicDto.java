package br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MunicipioBasicDto {

	private Long id;
	private String descricao;
	private String codigoIbge;
	private EstadoBasicDto estado;
	
	public MunicipioBasicDto() {
	}

	public MunicipioBasicDto(Long id, String descricao, String codigoIbge, EstadoBasicDto estado) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.codigoIbge = codigoIbge;
		this.estado = estado;
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
		MunicipioBasicDto other = (MunicipioBasicDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
