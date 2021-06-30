package br.gov.pa.prodepa.pae.protocolo.application.controller;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.pa.prodepa.pae.protocolo.application.service.DocumentoProtocoladoApplicationService;
import br.gov.pa.prodepa.pae.protocolo.application.service.ProtocoloApplicationService;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocolarDocumentoDto;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/protocolos")
public class ProtocoloController {

	private final DocumentoProtocoladoApplicationService documentoProtocoladoService;
	private Publisher<Message<String>> jmsReactiveSource;
	private final ProtocoloApplicationService protocoloService;
	
	@Autowired
	public ProtocoloController(DocumentoProtocoladoApplicationService documentoProtocoladoService, Publisher<Message<String>> jmsReactiveSource, ProtocoloApplicationService protocoloService) {
		super();
		this.documentoProtocoladoService = documentoProtocoladoService;
		this.jmsReactiveSource = jmsReactiveSource;
		this.protocoloService = protocoloService;
	}

	@PostMapping
	public void protocolarDococumento(@RequestBody ProtocolarDocumentoDto protocolarDocumentoDto) {
		documentoProtocoladoService.protocolarDococumento(protocolarDocumentoDto);
	}
	
	@GetMapping(value = "/notifications", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getPatientAlerts() {
        return Flux.from(jmsReactiveSource)
        		.log()
        		.map( msg -> {
        			return msg.getPayload();
        		});
    }

	@GetMapping("/sequencia/iniciada")
	public boolean jaExisteSequenciaDeProtocoloIniciada(){
		return protocoloService.jaExisteSequenciaDeProtocoloIniciada();
	}
}
