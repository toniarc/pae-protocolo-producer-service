package br.gov.pa.prodepa.pae.protocolo.adapter.webclient;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.gov.pa.prodepa.nucleopa.client.MunicipioBasicDto;
import br.gov.pa.prodepa.nucleopa.client.PessoaFisicaBasicDto;
import br.gov.pa.prodepa.nucleopa.client.PessoaJuridicaBasicDto;
import br.gov.pa.prodepa.pae.common.rest.exception.RestTemplateResponseErrorHandler;
import br.gov.pa.prodepa.pae.protocolo.domain.port.NucleopaService;

@Component
public class NucleopaWebClientAdapter implements NucleopaService {

	private final RestTemplate restTemplate;
	
	@Value("${nucleopa.service.host}")
	private String NUCLEOPA_SERVICE_HOST;
	
	@Autowired
	public NucleopaWebClientAdapter(RestTemplateBuilder restTemplateBuilder) {
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
	
}
