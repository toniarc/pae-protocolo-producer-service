package br.gov.pa.prodepa.pae.protocolo.domain.port;

import java.util.List;
import java.util.Set;

import br.gov.pa.prodepa.nucleopa.client.MunicipioBasicDto;
import br.gov.pa.prodepa.nucleopa.client.PessoaFisicaBasicDto;
import br.gov.pa.prodepa.nucleopa.client.PessoaJuridicaBasicDto;

public interface NucleopaService {

	List<PessoaFisicaBasicDto> buscarPessoaFisicaPorId(Set<Long> ids);

	List<PessoaJuridicaBasicDto> buscarPessoaJuridicaPorId(Set<Long> ids);

	List<MunicipioBasicDto> buscarMunicipioPorId(Set<Long> ids);

}
