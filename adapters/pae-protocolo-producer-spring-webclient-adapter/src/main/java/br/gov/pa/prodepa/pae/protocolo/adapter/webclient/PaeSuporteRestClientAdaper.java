package br.gov.pa.prodepa.pae.protocolo.adapter.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.gov.pa.prodepa.pae.common.domain.dto.OrgaoPaeBasicDto;
import br.gov.pa.prodepa.pae.common.rest.client.exception.RestTemplateResponseErrorHandler;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ConsultaPaginadaDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.SetorBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.port.PaeSuporteService;

@Component
public class PaeSuporteRestClientAdaper implements PaeSuporteService{

	private final RestTemplate restTemplate;
	
	@Value("${pae.suporte.service.host}")
	private String PAE_SUPORTE_SERVICE_HOST;
	
	@Autowired
	public PaeSuporteRestClientAdaper(RestTemplateBuilder restTemplateBuilder) {
		restTemplate = restTemplateBuilder
		          .errorHandler(new RestTemplateResponseErrorHandler())
		          .build();
	}

	@Override
	public ConsultaPaginadaDto<OrgaoPaeBasicDto> buscarOrgaoPorNomeOuSiglaOuCnpj(String nome, String sigla,
			String cnpj, int pageSize, int pageNumber) {
		
		StringBuilder params = new StringBuilder("?");
		params.append("pageSize=" + pageSize);
		params.append("&pageNumber=" + pageNumber);
		if(nome != null) params.append("&nome=" + nome);
		if(sigla != null) params.append("&sigla=" + sigla);
		if(cnpj != null) params.append("&cnpj=" + cnpj);
		
		String fooResourceUrl = PAE_SUPORTE_SERVICE_HOST + "/pae-suporte-service/orgaos-pae/formato-basico/filtrar-por-nome-cnpj-sigla" + params.toString();
		ResponseEntity<ConsultaPaginadaDto<OrgaoPaeBasicDto>> exchange = restTemplate.exchange(fooResourceUrl, HttpMethod.GET, null, new ParameterizedTypeReference<ConsultaPaginadaDto<OrgaoPaeBasicDto>>() {});
		return exchange.getBody();
	}
	
	@Override
	public ConsultaPaginadaDto<SetorBasicDto> buscarSetorPorNomeOuSigla(String nome, String sigla, int pageSize, int pageNumber) {
		
		StringBuilder params = new StringBuilder("?");
		params.append("pageSize=" + pageSize);
		params.append("&pageNumber=" + pageNumber);
		if(nome != null) params.append("&nome=" + nome);
		if(sigla != null) params.append("&sigla=" + sigla);
		
		String fooResourceUrl = PAE_SUPORTE_SERVICE_HOST + "/pae-suporte-service/setores/lista-setores-ativos-por-orgao-usuario" + params.toString();
		ResponseEntity<ConsultaPaginadaDto<SetorBasicDto>> exchange = restTemplate.exchange(fooResourceUrl, HttpMethod.GET, null, new ParameterizedTypeReference<ConsultaPaginadaDto<SetorBasicDto>>() {});
		return exchange.getBody();
	}

}
