package br.gov.pa.prodepa.pae.protocolo.domain.validator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import br.gov.pa.prodepa.pae.common.domain.exception.DomainException;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.documento.ModeloEstruturaBasicDto;

public class ProtocoloUtil {

	public static void substituirCamposDinamicos(ProtocoloDto protocoloDto) {
		ModeloEstruturaBasicDto modeloEstrutura = protocoloDto.getDocumento().getModeloConteudo().getModeloEstrutura();
		modeloEstrutura.setCabecalho(substituirCamposDinamicos(modeloEstrutura.getCabecalho(), protocoloDto));
		modeloEstrutura.setRodape(substituirCamposDinamicos(modeloEstrutura.getRodape(), protocoloDto));

		protocoloDto.getDocumento().setConteudo( substituirCamposDinamicos(protocoloDto.getDocumento().getConteudo(), protocoloDto));
	}
	
	private static String substituirCamposDinamicos(String texto, ProtocoloDto protocoloDto) {
		
		if(texto == null) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		Pattern pattern = Pattern.compile("(%24%7B([A-Z_]*)%7D)");
		Matcher matcher = pattern.matcher(texto);
		
		while(matcher.find()) {
			String campoDinamico = matcher.group(2);
			
			if(!campoDinamico.equals("DATA_PROTOCOLO") && !campoDinamico.equals("NUMERO_PROTOCOLO")) {
				String value = getValue(campoDinamico, protocoloDto);
				matcher.appendReplacement(sb, value);
			}
			
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	private static String getValue(String campoDinamico, ProtocoloDto dto) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		if (campoDinamico.trim().equals("BRASAO_ESTADO")) {
			
			try {
				InputStream resourceAsStream = ProtocoloDto.class.getClassLoader().getResourceAsStream("images/brasao_estado_uri_encoded.txt");
				return new BufferedReader(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8))
				.lines()
				.collect(Collectors.joining());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		//TODO ALTERAR
		if (campoDinamico.trim().equals("BRASAO_ORGAO")) {
			return uriEncode("<img src=\"\"> </img>");
		}
		
		if(campoDinamico.trim().equals("HIERARQUIA_SETOR") ) {
			return uriEncode(dto.getLocalizacaoOrigem().getNomeHierarquiaCompleta());
		}
		
		if(campoDinamico.trim().equals("ESPECIE") ) {
			return uriEncode(dto.getEspecie().getNome());
		}
		
		if(campoDinamico.trim().equals("NUMERO_DOCUMENTO") ) {
			return uriEncode(dto.getDocumentoProtocolado().getAno() + "/" + dto.getDocumentoProtocolado().getNumero());
		}
		
		if(campoDinamico.trim().equals("ORGAO_ORIGEM_SIGLA") ) {
			return uriEncode(dto.getOrgaoOrigem().getSigla());
		}
		
		if(campoDinamico.trim().equals("ORGAO_ORIGEM_NOME") ) {
			return uriEncode(dto.getOrgaoOrigem().getNome());
		}
		
		if(campoDinamico.trim().equals("SETOR_ORIGEM_SIGLA") ) {
			return uriEncode(dto.getLocalizacaoOrigem().getSigla());
		}
		
		if(campoDinamico.trim().equals("SETOR_ORIGEM_DESCRICAO") ) {
			return uriEncode(dto.getLocalizacaoOrigem().getNome());
		}
		
		if(campoDinamico.trim().equals("SETOR_ORIGEM_ENDERECO") ) {
			return uriEncode(dto.getLocalizacaoOrigem().formatarEndereco());
		}
		
		if(campoDinamico.trim().equals("ORGAO_DESTINO_SIGLA") ) {
			return uriEncode(dto.getOrgaoDestino().getSigla());
		}
		
		if(campoDinamico.trim().equals("ORGAO_DESTINO_NOME") ) {
			return uriEncode(dto.getOrgaoDestino().getNome());
		}
		
		if(campoDinamico.trim().equals("SETOR_DESTINO_SIGLA") ) {
			return uriEncode(dto.getLocalizacaoDestino().getSigla());
		}
		
		if(campoDinamico.trim().equals("SETOR_DESTINO_DESCRICAO") ) {
			return uriEncode(dto.getLocalizacaoDestino().getNome());
		}
		
		if(campoDinamico.trim().equals("SETOR_DESTINO_ENDERECO") ) {
			return uriEncode(dto.getLocalizacaoDestino().formatarEndereco());
		}
		
		if(campoDinamico.trim().equals("ASSUNTO_DESCRICAO") ) {
			return uriEncode(dto.getAssunto().getNome());
		}
		
		if(campoDinamico.trim().equals("CARGO_USUARIO") ) {
			return uriEncode("Alterar para o cargo do usuario");
		}
		
		//TODO ALTERAR
		if(campoDinamico.trim().equals("RESPONSAVEL_SETOR_DESTINO") ) {
			return uriEncode("Alterar para o responsavel setor destino");
		}
		
		if(campoDinamico.trim().equals("DATA_DOCUMENTO") ) {
			return uriEncode(sdf.format(dto.getDocumentoProtocolado().getDataCadastro()));
		}
		
		if(campoDinamico.trim().equals("DATA_PROTOCOLO") ) {
			return uriEncode(sdf.format(dto.getDataProtocolo()));
		}
		
		if(campoDinamico.trim().equals("NUMERO_PROTOCOLO") ) {
			return dto.getAnoProtocolo() + "/" + dto.getNumeroProtocolo();
		}
		
		throw new DomainException("O campo dinamico " + campoDinamico + " nao foi encontrado.");
	}
	
	private static String uriEncode(String text) {
		try {
			return URLEncoder.encode(text, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String substituirCamposDinamicosProtocolo(String texto, ProtocoloDto protocoloDto) {
		StringBuilder sb = new StringBuilder();
		
		Pattern pattern = Pattern.compile("(%24%7B([A-Z_]*)%7D)");
		Matcher matcher = pattern.matcher(texto);
		
		while(matcher.find()) {
			String campoDinamico = matcher.group(2);
			
			if(campoDinamico.equals("DATA_PROTOCOLO") || campoDinamico.equals("NUMERO_PROTOCOLO")) {
				String value = getValue(campoDinamico, protocoloDto);
				matcher.appendReplacement(sb, value);
			}
			
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
}
