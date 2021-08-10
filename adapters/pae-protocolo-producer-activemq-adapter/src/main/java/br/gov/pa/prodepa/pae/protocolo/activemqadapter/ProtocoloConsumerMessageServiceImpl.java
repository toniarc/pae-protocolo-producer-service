package br.gov.pa.prodepa.pae.protocolo.activemqadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.domain.service.ProtocoloService;

@Component
public class ProtocoloConsumerMessageServiceImpl {
	
	@Autowired
	private ProtocoloService service;

	@JmsListener(destination = "protocolar-documento")
	public void processMessage(ProtocoloDto dto) {

		service.protocolarDocumento(dto);
		
	}
}
