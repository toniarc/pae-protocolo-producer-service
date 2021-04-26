package br.gov.pa.prodepa.pae.protocolo.activemqadapter.producer;

import java.net.ConnectException;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.pa.prodepa.pae.protocolo.client.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocolarDocumentoDto;
import br.gov.pa.prodepa.pae.protocolo.domain.port.MessagingService;
import br.gov.pa.prodepa.pae.suporte.client.PaeSuporteEnricherDto;

@Component
public class MessagingServiceImpl implements MessagingService{

	@Autowired
    public JmsTemplate jmsTemplate;
	
	@Autowired
    public JmsMessagingTemplate jmsMessagingTemplate;
	
	@Override
	public void enviarParaFilaProtocolarDocumento(ProtocoloDto protocoloDto, String correlationId) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			
			jmsTemplate.convertAndSend("protocolar-documento", protocoloDto, new MessagePostProcessor() {
				
				@Override
				public Message postProcessMessage(Message message) throws JMSException {
					message.setJMSCorrelationID(correlationId);
					return message;
				}
			});
			
			/*
			String dtoAsString = objectMapper.writeValueAsString(protocoloDto);
			
			jmsTemplate.send("protocolar-documento", new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage msg = session.createTextMessage(dtoAsString);
					msg.setJMSCorrelationID(correlationId);
					return msg;
				}
			});*/
			
			//this.jmsTemplate.convertAndSend("protocolar-documento", dtoAsString);
		} catch (Exception e) {
			if(e.getCause().getCause() instanceof ConnectException) {
				throw new RuntimeException("Nao foi possivel contar-se ao broker jms", e);
			}
			
			throw new RuntimeException(e);
		}
		
	}

	public String enriquecerModeloDadosSuporte(ProtocolarDocumentoDto dto) {
		try {
			
			ObjectMapper objectMapper = new ObjectMapper();
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

	@Override
	public PaeSuporteEnricherDto enriquecerModeloDadosSuporte(PaeSuporteEnricherDto enricher) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String dtoAsString = objectMapper.writeValueAsString(enricher);
			
			Message returnMessage = this.jmsTemplate.sendAndReceive("paesuporteservice.enriquecerdados", new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message responseMsg = session.createTextMessage(dtoAsString);
	                responseMsg.setJMSCorrelationID(UUID.randomUUID().toString());
	                return responseMsg;
				}
			});
			
			if(returnMessage == null) {
				System.out.println("return message is null");
				return null;
			}
			
			String body = (String) ((ActiveMQTextMessage) returnMessage).getText();
			return objectMapper.readValue(body, PaeSuporteEnricherDto.class);
			
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
