package br.gov.pa.prodepa.pae.protocolo.activemqadapter.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.messaging.Message;

@Configuration
public class ProtocoloProducerJmsConfig {

	@Value("${spring.activemq.broker-url}")
	private String brokerUrl;
	
	@Value("${spring.activemq.user}")
	private String userName;
	
	@Value("${spring.activemq.password}")
	private String password;
	
	@Bean
    public ConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUserName(userName);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		
		System.out.println("################ CREATE A MESSAGE CONVERTER ##################");
		
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}
	
    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory());
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
        return jmsTemplate;
    }
    
    @Bean
    public JmsListenerContainerFactory<?> jsaFactory(DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setMessageConverter(jacksonJmsMessageConverter());
        configurer.configure(factory, connectionFactory());
        return factory;
    }
    
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        //factory.setDestinationResolver(destinationResolver());
        factory.setSessionTransacted(true);
        factory.setConcurrency("3-10");
        factory.setMessageConverter(jacksonJmsMessageConverter());
        return factory;
    }
	
	@Bean
    public Publisher<Message<String>> jmsReactiveSource() {
        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(this.connectionFactory())
                        .destination("notificacao")
                	)
                //.transform(Transformers.fromJson(ProtocoloResponseDto.class))
                //.filter( m -> {
                	//System.out.println(m);
                //	return ((ProtocoloResponseDto) m).getUsuarioId().equals(3199L);
                //})
                .channel(MessageChannels.queue())
                //.log(LoggingHandler.Level.DEBUG)
                //.log()
                .toReactivePublisher();
    }
}
