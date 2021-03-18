package br.gov.pa.prodepa.pae.protocolo.activemqadapter.producer;

import java.net.ConnectException;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocolarDocumentoDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.domain.port.MessagingService;

@Component
public class ProtocoloProducer implements MessagingService{

	@Autowired
    public JmsTemplate jmsTemplate;
	
	@Autowired
    public JmsMessagingTemplate jmsMessagingTemplate;
	
	@Override
	public void sendMessage(ProtocoloDto protocoloDto) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String dtoAsString = objectMapper.writeValueAsString(protocoloDto);
			this.jmsTemplate.convertAndSend("protocolar-documento", dtoAsString);
		} catch (Exception e) {
			if(e.getCause() instanceof JMSException) {
				if(e.getCause().getCause() instanceof ConnectException) {
					throw new RuntimeException("Nao foi possivel contar-se ao broker jms");
				}
			}
			
			throw new RuntimeException(e);
		}
		
	}

    private final SimpleMessageConverter converter = new SimpleMessageConverter();

	
	@Override
	public String enriquecerModeloDadosSuporte(ProtocolarDocumentoDto dto) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String dtoAsString = objectMapper.writeValueAsString(dto);
			
			Message returnMessage = this.jmsTemplate.sendAndReceive("enriquecer-documento-protocolado", new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message responseMsg = session.createTextMessage(dtoAsString);
	                responseMsg.setJMSCorrelationID(UUID.randomUUID().toString());
	                return responseMsg;
				}
			});
			
			return (String) ((ActiveMQTextMessage) returnMessage).getText();
			
		} catch (Exception e) {
			if(e.getCause() instanceof JMSException) {
				if(e.getCause().getCause() instanceof ConnectException) {
					throw new RuntimeException("Nao foi possivel contar-se ao broker jms");
				}
			}
			
			throw new RuntimeException(e);
		}
	}

}
