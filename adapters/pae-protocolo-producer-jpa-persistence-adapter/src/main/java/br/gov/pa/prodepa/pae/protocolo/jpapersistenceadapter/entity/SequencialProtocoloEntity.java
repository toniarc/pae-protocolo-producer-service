package br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sequencial_protocolo", schema = "pae")
public class SequencialProtocoloEntity {

	private Integer ano;
	private Long sequencial;

	@Id
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public Long getSequencial() {
		return sequencial;
	}
	public void setSequencial(Long sequencial) {
		this.sequencial = sequencial;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ano == null) ? 0 : ano.hashCode());
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
		SequencialProtocoloEntity other = (SequencialProtocoloEntity) obj;
		if (ano == null) {
			if (other.ano != null)
				return false;
		} else if (!ano.equals(other.ano))
			return false;
		return true;
	}
	
	
}
