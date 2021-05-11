package br.gov.pa.prodepa.pae.protocolo.domain.dto;

import br.gov.pa.prodepa.pae.common.domain.dto.OrgaoPaeBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.PessoaFisicaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.PessoaJuridicaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.SetorBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.model.TipoInteressado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class InteressadoDto {

	private Long id;
	private String nome;
	private String sigla;
	private String cpfCnpj;
	private TipoInteressado tipoInteressado;
	
	public InteressadoDto(PessoaFisicaBasicDto pf){
		this.cpfCnpj = pf.getCpf();
		this.id = pf.getId();
		this.nome = pf.getNome();
		this.tipoInteressado = TipoInteressado.PESSOA_FISICA;
	}
	
	public InteressadoDto(PessoaJuridicaBasicDto pj){
		this.cpfCnpj = pj.getCnpj();
		this.id = pj.getId();
		this.nome = pj.getNome();
		this.tipoInteressado = TipoInteressado.PESSOA_JURIDICA;
	}
	
	public InteressadoDto(OrgaoPaeBasicDto orgao){
		this.cpfCnpj = orgao.getCnpj();
		this.id = orgao.getId();
		this.nome = orgao.getNome();
		this.sigla = orgao.getSigla();
		this.tipoInteressado = TipoInteressado.ORGAO;
	}
	
	public InteressadoDto(SetorBasicDto setor){
		this.id = setor.getId();
		this.nome = setor.getNome();
		this.sigla = setor.getSigla();
		this.tipoInteressado = TipoInteressado.SETOR;
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
		InteressadoDto other = (InteressadoDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
