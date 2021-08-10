package br.gov.pa.prodepa.pae.protocolo.domain.port;

import java.util.List;
import java.util.Set;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ConsultaPaginadaDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.MunicipioBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.PessoaFisicaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.PessoaJuridicaBasicDto;

public interface NucleopaRestClient {

	List<PessoaFisicaBasicDto> buscarPessoaFisicaPorId(Set<Long> ids);

	List<PessoaJuridicaBasicDto> buscarPessoaJuridicaPorId(Set<Long> ids);

	List<MunicipioBasicDto> buscarMunicipioPorId(Set<Long> ids);

	ConsultaPaginadaDto<PessoaFisicaBasicDto> buscarPessoaFisicaPorNomeOuCpf(String nome, String cpf, Integer pageSize, Integer pageNumber);

	ConsultaPaginadaDto<PessoaJuridicaBasicDto> buscarPessoaJuridicaPorNomeOuCnpj(String nome, String cpfCnpj,
			int pageSize, int pageNumber);

	MunicipioBasicDto buscarMunicipioPorCodigoIbge(String codigoIbge);

}
