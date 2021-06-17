package br.gov.pa.prodepa.pae.protocolo.activemqadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.domain.service.ProtocoloService;

@Component
public class ProtocoloConsumerMessageServiceImpl {
	
	@Autowired
	private ProtocoloService service;

	@JmsListener(destination = "protocolar-documento")
	public void processMessage(ProtocoloDto dto) {

		service.protocolarDocumento(dto);
		/*
		ObjectMapper objectMapper = new ObjectMapper();
		ProtocoloDto dto;
		try {
			dto = objectMapper.readValue(json, ProtocoloDto.class);
			service.protocolarDocumento(dto);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}*/
	}
}
