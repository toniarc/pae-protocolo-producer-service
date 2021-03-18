package br.gov.pa.prodepa.pae.protocolo.application.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.gov.pa.prodepa.pae.protocolo.domain.model.Usuario;
import br.gov.pa.prodepa.pae.protocolo.domain.port.DocumentoProtocoladoRepository;
import br.gov.pa.prodepa.pae.protocolo.domain.port.PaeDocumentoService;
import br.gov.pa.prodepa.pae.protocolo.domain.port.MessagingService;
import br.gov.pa.prodepa.pae.protocolo.domain.port.SequencialDocumentoRepository;
import br.gov.pa.prodepa.pae.protocolo.domain.port.TransactionalService;
import br.gov.pa.prodepa.pae.protocolo.domain.service.DocumentoProtocoladoDomainService;
import br.gov.pa.prodepa.pae.protocolo.domain.service.ProtocoloService;
import br.gov.pa.prodepa.pae.protocolo.domain.service.SequencialDocumentoDomainService;
import br.gov.pa.prodepa.pae.protocolo.domain.service.SequencialDocumentoService;

@Configuration
public class DomainRegistry {
	
	@Autowired
	private final ApplicationContext applicationContext;
	
	@Autowired
	public DomainRegistry(ApplicationContext applicationContext) {
		super();
		this.applicationContext = applicationContext;
	}

	@Bean
	public ProtocoloService criarProtocoloService() {
		return new DocumentoProtocoladoDomainService(
				applicationContext.getBean(MessagingService.class),
				applicationContext.getBean(Usuario.class),
				applicationContext.getBean(SequencialDocumentoRepository.class),
				applicationContext.getBean(PaeDocumentoService.class), 
				applicationContext.getBean(DocumentoProtocoladoRepository.class),
				applicationContext.getBean(SequencialDocumentoService.class));
	}
	
	@Bean
	public SequencialDocumentoService criarSequencialDocumentoService() {
		return new SequencialDocumentoDomainService(
				applicationContext.getBean(SequencialDocumentoRepository.class),
				applicationContext.getBean(Usuario.class),
				applicationContext.getBean(TransactionalService.class) 
				);
	}
}
	