package br.gov.pa.prodepa.pae.protocolo.adapter.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.gov.pa.prodepa.pae.documento.client.DocumentoBasicDto;
import br.gov.pa.prodepa.pae.documento.client.ModeloEstruturaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.port.PaeDocumentoService;

@Component
public class DocumentoWebClientAdapter implements PaeDocumentoService {
	
	private final RestTemplate restTemplate;
	
	@Value("${documento.service.host}")
	private String DOCUMENTO_SERVICE_HOST;
	
	@Autowired
	public DocumentoWebClientAdapter() {
		restTemplate = new RestTemplate();
	}

	@Override
	public DocumentoBasicDto buscarDocumentoPorId(Long id) {
		String fooResourceUrl = DOCUMENTO_SERVICE_HOST + "/pae-documento-service/documentos/" + id;
		ResponseEntity<DocumentoBasicDto> response = restTemplate.getForEntity(fooResourceUrl, DocumentoBasicDto.class);
		return response.getBody();
	}
	
	@Override
	public ModeloEstruturaBasicDto buscarModeloEstruturaPorId(Long id) {
		String fooResourceUrl = DOCUMENTO_SERVICE_HOST + "/pae-documento-service/modelos-estrutura/pesquisas/por-modelo-conteudo/" + id;
		ResponseEntity<ModeloEstruturaBasicDto> response = restTemplate.getForEntity(fooResourceUrl, ModeloEstruturaBasicDto.class);
		return response.getBody();
	}
}
