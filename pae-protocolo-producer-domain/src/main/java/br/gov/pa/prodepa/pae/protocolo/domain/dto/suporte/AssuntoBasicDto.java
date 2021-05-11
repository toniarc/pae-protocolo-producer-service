package br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssuntoBasicDto {

	private Long id;
	private String codigo;
	private String nome;
	private String nomeHierarquiaCompleta;
	private String classeCodigo;
	
	public AssuntoBasicDto() {
	}
	
	@Builder
	public AssuntoBasicDto(Long id, String codigo, String nome, String nomeHierarquiaCompleta, String classeCodigo) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nome = nome;
		this.nomeHierarquiaCompleta = nomeHierarquiaCompleta;
		this.classeCodigo = classeCodigo;
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
		AssuntoBasicDto other = (AssuntoBasicDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
