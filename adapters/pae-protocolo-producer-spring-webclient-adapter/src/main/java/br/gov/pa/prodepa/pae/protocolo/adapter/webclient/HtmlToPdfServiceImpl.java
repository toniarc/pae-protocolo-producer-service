package br.gov.pa.prodepa.pae.protocolo.adapter.webclient;

import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.documento.ModeloEstruturaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.port.Html2PdfService;

@Component
public class HtmlToPdfServiceImpl implements Html2PdfService {

	@Value("${html2pdf.host}")
	private String html2pdfHost;
	
	private String contextPath = "/html2pdf";
	
	public byte[] gerarPdf(String conteudo, String cabecalho, String rodape, String margemTopo, String margemRodape, String margemEsquerda, String margemDireita, String formato) {
		return sendRequest(html2pdfHost + contextPath, conteudo, cabecalho, rodape, margemTopo, margemRodape, margemEsquerda, margemDireita, formato);
	}
	
	public byte[] gerarThumbnail(String conteudo, String cabecalho, String rodape, String margemTopo, String margemRodape, String margemEsquerda, String margemDireita, String formato) {
		return sendRequest(html2pdfHost + contextPath + "/thumbnail", conteudo, cabecalho, rodape, margemTopo, margemRodape, margemEsquerda, margemDireita, formato);
	}

	private byte[] sendRequest(final String uri, String conteudo, String cabecalho, String rodape, String margemTopo, String margemRodape, String margemEsquerda, String margemDireita, String formato) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		try {
			MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
			map.add("content", URLDecoder.decode(conteudo, "UTF-8"));
			map.add("header", URLDecoder.decode(cabecalho, "UTF-8"));
			map.add("footer", URLDecoder.decode(rodape, "UTF-8"));
			map.add("margin-top", margemTopo);
			map.add("margin-bottom", margemRodape);
			map.add("margin-left", margemEsquerda);
			map.add("margin-right", margemDireita);
			map.add("format", formato);
			
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<byte[]> response = restTemplate.postForEntity(uri, request, byte[].class);
			return response.getBody();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] gerarPdf(String conteudo, ModeloEstruturaBasicDto me) {
		return gerarPdf(conteudo, me.getCabecalho(), me.getRodape(),
				me.getMargens().getTopo(), me.getMargens().getRodape(), me.getMargens().getEsquerda(),
				me.getMargens().getDireita(), me.getFormato().getDescricao());
	}

}