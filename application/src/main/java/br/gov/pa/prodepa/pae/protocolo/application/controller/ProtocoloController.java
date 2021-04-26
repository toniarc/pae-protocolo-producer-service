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

import br.gov.pa.prodepa.pae.protocolo.application.service.ProtocoloApplicationService;
import br.gov.pa.prodepa.pae.protocolo.client.dto.ProtocoloResponseDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocolarDocumentoDto;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/protocolos")
public class ProtocoloController {

	private final ProtocoloApplicationService service;
	
	private Publisher<Message<ProtocoloResponseDto>> jmsReactiveSource;
	
	@Autowired
	public ProtocoloController(ProtocoloApplicationService service, Publisher<Message<ProtocoloResponseDto>> jmsReactiveSource) {
		super();
		this.service = service;
		this.jmsReactiveSource = jmsReactiveSource;
	}

	@PostMapping
	public void protocolarDococumento(@RequestBody ProtocolarDocumentoDto protocolarDocumentoDto) {
		service.protocolarDococumento(protocolarDocumentoDto);
	}
	
	@GetMapping(value = "/notifications", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProtocoloResponseDto> getPatientAlerts() {
        return Flux.from(jmsReactiveSource)
        		.log()
        		.map( msg -> { 
        			return msg.getPayload();
        		});
        		//.filter( p -> {
        		//	return p.getUsuarioCadastro().getId().equals(userId);
        		//});
    }
}
