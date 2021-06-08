package br.gov.pa.prodepa.pae.protocolo.activemqadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloResponseDto;
import br.gov.pa.prodepa.pae.protocolo.domain.port.NotificationMessageService;

@Component
public class NotificationMessageServiceImpl implements NotificationMessageService{

	@Autowired
    public JmsTemplate jmsTemplate;
	
	@Override
	public void send(ProtocoloResponseDto protocoloSalvo) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(protocoloSalvo);
			jmsTemplate.send("notificacao", session -> {
				return session.createTextMessage(json);
			});
			
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
