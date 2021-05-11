package br.gov.pa.prodepa.pae.protocolo.domain.dto.documento;

import java.io.Serializable;

import br.gov.pa.prodepa.pae.common.domain.dto.AuditoriaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoBasicDto implements Serializable {

	private static final long serialVersionUID = 3129901027036728047L;
	
	private Long id;
	private Integer ano;
	private Long sequencial;
	private String conteudo;
	private ModeloConteudoBasicDto modeloConteudo;
	private AuditoriaDto auditoria;
	
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
		DocumentoBasicDto other = (DocumentoBasicDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
