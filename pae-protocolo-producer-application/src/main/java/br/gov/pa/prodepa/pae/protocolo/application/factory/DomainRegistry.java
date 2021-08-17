package br.gov.pa.prodepa.pae.protocolo.application.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.gov.pa.prodepa.pae.common.domain.dto.UsuarioDto;
import br.gov.pa.prodepa.pae.protocolo.domain.port.ControleAcessoService;
import br.gov.pa.prodepa.pae.protocolo.domain.port.DocumentoProtocoladoRepository;
import br.gov.pa.prodepa.pae.protocolo.domain.port.Html2PdfService;
import br.gov.pa.prodepa.pae.protocolo.domain.port.NotificationMessageService;
import br.gov.pa.prodepa.pae.protocolo.domain.port.NucleopaRestClient;
import br.gov.pa.prodepa.pae.protocolo.domain.port.ObjectStorageService;
import br.gov.pa.prodepa.pae.protocolo.domain.port.PaeDocumentoService;
import br.gov.pa.prodepa.pae.protocolo.domain.port.PaeSuporteService;
import br.gov.pa.prodepa.pae.protocolo.domain.port.ProtocoloProducerMessageService;
import br.gov.pa.prodepa.pae.protocolo.domain.port.ProtocoloRepository;
import br.gov.pa.prodepa.pae.protocolo.domain.port.SequencialDocumentoRepository;
import br.gov.pa.prodepa.pae.protocolo.domain.port.TransactionalService;
import br.gov.pa.prodepa.pae.protocolo.domain.service.DocumentoProtocoladoDomainService;
import br.gov.pa.prodepa.pae.protocolo.domain.service.InteressadoDomainService;
import br.gov.pa.prodepa.pae.protocolo.domain.service.InteressadoService;
import br.gov.pa.prodepa.pae.protocolo.domain.service.ProtocoloDomainServce;
import br.gov.pa.prodepa.pae.protocolo.domain.service.ProtocoloService;
import br.gov.pa.prodepa.pae.protocolo.domain.service.SequencialDocumentoDomainService;
import br.gov.pa.prodepa.pae.protocolo.domain.service.SequencialDocumentoService;
import br.gov.pa.prodepa.pae.protocolo.domain.service.TipoDestinoDomainService;
import br.gov.pa.prodepa.pae.protocolo.domain.service.TipoDestinoService;

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
	public TipoDestinoService criarTipoDocumentoService() {
		return new TipoDestinoDomainService(applicationContext.getBean(UsuarioDto.class), applicationContext.getBean(PaeSuporteService.class));
	}
	
	@Bean
	public InteressadoService criarInteressadoService() {
		return new InteressadoDomainService(
				applicationContext.getBean(NucleopaRestClient.class),
				applicationContext.getBean(PaeSuporteService.class));
	}
	
	@Bean
	public ProtocoloService criarProtocoloService() {
		return new ProtocoloDomainServce(
				applicationContext.getBean(NotificationMessageService.class),
				applicationContext.getBean(ProtocoloRepository.class),
				applicationContext.getBean(TransactionalService.class),
				applicationContext.getBean(Html2PdfService.class),
				applicationContext.getBean(ObjectStorageService.class));
	}
	
	@Bean
	public DocumentoProtocoladoDomainService criarProtocoloDomainService() {
		return new DocumentoProtocoladoDomainService(
				applicationContext.getBean(ProtocoloProducerMessageService.class),
				applicationContext.getBean(UsuarioDto.class),
				applicationContext.getBean(SequencialDocumentoRepository.class),
				applicationContext.getBean(PaeDocumentoService.class), 
				applicationContext.getBean(DocumentoProtocoladoRepository.class),
				applicationContext.getBean(SequencialDocumentoService.class),
				applicationContext.getBean(NucleopaRestClient.class),
				applicationContext.getBean(PaeSuporteService.class),
				applicationContext.getBean(ControleAcessoService.class));
	}
	
	@Bean
	public SequencialDocumentoService criarSequencialDocumentoService() {
		return new SequencialDocumentoDomainService(
				applicationContext.getBean(SequencialDocumentoRepository.class),
				applicationContext.getBean(UsuarioDto.class),
				applicationContext.getBean(TransactionalService.class) 
				);
	}
}
	