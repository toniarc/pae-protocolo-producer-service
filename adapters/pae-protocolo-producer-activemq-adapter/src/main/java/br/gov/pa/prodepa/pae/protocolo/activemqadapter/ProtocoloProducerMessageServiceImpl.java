package br.gov.pa.prodepa.pae.protocolo.activemqadapter;

import java.net.ConnectException;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.domain.port.ProtocoloProducerMessageService;

@Component
public class ProtocoloProducerMessageServiceImpl implements ProtocoloProducerMessageService{

	@Autowired
    public JmsTemplate jmsTemplate;
	
	public void enviarParaFilaProtocolarDocumento(ProtocoloDto protocoloDto, String correlationId) {
		
		try {
			
			jmsTemplate.convertAndSend("protocolar-documento", protocoloDto, new MessagePostProcessor() {
				@Override
				public Message postProcessMessage(Message message) throws JMSException {
					message.setJMSCorrelationID(correlationId);
					return message;
				}
			});
			
		} catch (Exception e) {
			if(e.getCause().getCause() instanceof ConnectException) {
				throw new RuntimeException("Nao foi possivel contar-se ao broker jms", e);
			}
			
			throw new RuntimeException(e);
		}
		
	}
}
