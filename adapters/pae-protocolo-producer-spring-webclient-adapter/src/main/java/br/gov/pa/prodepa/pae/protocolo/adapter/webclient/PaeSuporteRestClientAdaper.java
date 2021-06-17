package br.gov.pa.prodepa.pae.protocolo.adapter.webclient;

import java.util.List;
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
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.AssuntoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.EspecieBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.LocalizacaoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.OrgaoPaeBasicDto;
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

	@Override
	public EspecieBasicDto buscarEspecie(Long especieId) {
		String fooResourceUrl = PAE_SUPORTE_SERVICE_HOST + "/pae-suporte-service/especies/" + especieId;
		ResponseEntity<EspecieBasicDto> exchange = restTemplate.exchange(fooResourceUrl, HttpMethod.GET, null, EspecieBasicDto.class);
		return exchange.getBody();
	}

	@Override
	public AssuntoBasicDto buscarAssunto(Long assuntoId) {
		String fooResourceUrl = PAE_SUPORTE_SERVICE_HOST + "/pae-suporte-service/assuntos/" + assuntoId;
		ResponseEntity<AssuntoBasicDto> exchange = restTemplate.exchange(fooResourceUrl, HttpMethod.GET, null, AssuntoBasicDto.class);
		return exchange.getBody();
	}

	@Override
	public List<OrgaoPaeBasicDto> buscarOrgaos(List<Long> orgaosIds) {
		StringBuilder params = new StringBuilder("?");
		params.append("ids=" + String.join(",", orgaosIds.stream().map(id -> id.toString()).collect(Collectors.toList())));
		
		String fooResourceUrl = PAE_SUPORTE_SERVICE_HOST + "/pae-suporte-service/orgaos-pae/com-setor-padrao-e-responsavel" + params.toString();
		ResponseEntity<List<OrgaoPaeBasicDto>> exchange = restTemplate.exchange(fooResourceUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<OrgaoPaeBasicDto>>() {});
		return exchange.getBody();
	}

	@Override
	public List<LocalizacaoBasicDto> buscarLocalizacoes(List<Long> localizacaoesIds) {
		StringBuilder params = new StringBuilder("?");
		params.append("ids=" + String.join(",", localizacaoesIds.stream().map(id -> id.toString()).collect(Collectors.toList())));
		
		String fooResourceUrl = PAE_SUPORTE_SERVICE_HOST + "/pae-suporte-service/localizacoes/formato-basico/pesquisas/com-endereco" + params.toString();
		ResponseEntity<List<LocalizacaoBasicDto>> exchange = restTemplate.exchange(fooResourceUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<LocalizacaoBasicDto>>() {});
		return exchange.getBody();
	}

	@Override
	public List<LocalizacaoBasicDto> buscarLocalizacoesUsuarioAtivas(Long id) {
		String fooResourceUrl = PAE_SUPORTE_SERVICE_HOST + "/pae-suporte-service/localizacoes/formato-basico/usuario-logado";
		ResponseEntity<List<LocalizacaoBasicDto>> exchange = restTemplate.exchange(fooResourceUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<LocalizacaoBasicDto>>() {});
		return exchange.getBody();
	}

}
