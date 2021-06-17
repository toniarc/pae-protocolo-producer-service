package br.gov.pa.prodepa.pae.protocolo.adapter.webclient;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.gov.pa.prodepa.pae.common.rest.client.exception.RestTemplateResponseErrorHandler;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ConsultaPaginadaDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.MunicipioBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.PessoaFisicaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.PessoaJuridicaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.port.NucleopaRestClient;

@Component
public class NucleopaRestClientAdapter implements NucleopaRestClient {

	private final RestTemplate restTemplate;
	
	@Value("${nucleopa.service.host}")
	private String NUCLEOPA_SERVICE_HOST;
	
	@Autowired
	public NucleopaRestClientAdapter(RestTemplateBuilder restTemplateBuilder) {
		restTemplate = restTemplateBuilder
		          .errorHandler(new RestTemplateResponseErrorHandler())
		          .build();
	}
	
	@Override
	public List<PessoaFisicaBasicDto> buscarPessoaFisicaPorId(Set<Long> ids) {
		String idCommaSeparated = ids.stream().map( id -> String.valueOf(id)).collect(Collectors.joining(","));
		String resourceUrl = NUCLEOPA_SERVICE_HOST + "/nucleopa-service/pessoas-fisicas/formato-basico?ids=" + idCommaSeparated;
		ResponseEntity<PessoaFisicaBasicDto[]> response = restTemplate.getForEntity(resourceUrl, PessoaFisicaBasicDto[].class);
		PessoaFisicaBasicDto[] body = response.getBody();
		return Arrays.asList(body);
	}

	@Override
	public List<PessoaJuridicaBasicDto> buscarPessoaJuridicaPorId(Set<Long> ids) {
		String idCommaSeparated = ids.stream().map( id -> String.valueOf(id)).collect(Collectors.joining(","));
		String fooResourceUrl = NUCLEOPA_SERVICE_HOST + "/nucleopa-service/pessoas-juridicas/formato-basico?ids=" + idCommaSeparated;
		ResponseEntity<PessoaJuridicaBasicDto[]> response = restTemplate.getForEntity(fooResourceUrl, PessoaJuridicaBasicDto[].class);
		PessoaJuridicaBasicDto[] body = response.getBody();
		return Arrays.asList(body);
	}
	
	@Override
	public List<MunicipioBasicDto> buscarMunicipioPorId(Set<Long> ids) {
		String idCommaSeparated = ids.stream().map( id -> String.valueOf(id)).collect(Collectors.joining(","));
		String fooResourceUrl = NUCLEOPA_SERVICE_HOST + "/nucleopa-service/municipios/formato-basico?ids=" + idCommaSeparated;
		ResponseEntity<MunicipioBasicDto[]> response = restTemplate.getForEntity(fooResourceUrl, MunicipioBasicDto[].class);
		MunicipioBasicDto[] body = response.getBody();
		return Arrays.asList(body);
	}

	@Override
	public ConsultaPaginadaDto<PessoaFisicaBasicDto> buscarPessoaFisicaPorNomeOuCpf(String nome, String cpf, Integer pageSize, Integer pageNumber) {
		StringBuilder params = new StringBuilder("?");
		params.append("pageSize=" + pageSize);
		params.append("&pageNumber=" + pageNumber);
		if(nome != null) params.append("&nome=" + nome);
		if(cpf != null) params.append("&cpf=" + cpf);
		
		String fooResourceUrl = NUCLEOPA_SERVICE_HOST + "/nucleopa-service/pessoas-fisicas/formato-basico/filtrar-por-nome-ou-cpf" + params.toString();
		ResponseEntity<ConsultaPaginadaDto<PessoaFisicaBasicDto>> exchange = restTemplate.exchange(fooResourceUrl, HttpMethod.GET, null, new ParameterizedTypeReference<ConsultaPaginadaDto<PessoaFisicaBasicDto>>() {});
		return exchange.getBody();
	}
	
	@Override
	public ConsultaPaginadaDto<PessoaJuridicaBasicDto> buscarPessoaJuridicaPorNomeOuCnpj(String nome, String cnpj,
			int pageSize, int pageNumber) {
		StringBuilder params = new StringBuilder("?");
		params.append("pageSize=" + pageSize);
		params.append("&pageNumber=" + pageNumber);
		if(nome != null) params.append("&nome=" + nome);
		if(cnpj != null) params.append("&cnpj=" + cnpj);
		
		String fooResourceUrl = NUCLEOPA_SERVICE_HOST + "/nucleopa-service/pessoas-juridicas/formato-basico/filtrar-por-nome-ou-cnpj" + params.toString();
		ResponseEntity<ConsultaPaginadaDto<PessoaJuridicaBasicDto>> exchange = restTemplate.exchange(fooResourceUrl, HttpMethod.GET, null, new ParameterizedTypeReference<ConsultaPaginadaDto<PessoaJuridicaBasicDto>>() {});
		return exchange.getBody();
	}

	@Override
	public MunicipioBasicDto buscarMunicipioPorCodigoIbge(String codigoIbge) {
		String fooResourceUrl = NUCLEOPA_SERVICE_HOST + "/nucleopa-service/municipios/formato-basico/codigo-ibge/" + codigoIbge;
		ResponseEntity<MunicipioBasicDto> response = restTemplate.getForEntity(fooResourceUrl, MunicipioBasicDto.class);
		return response.getBody();
	}
}
