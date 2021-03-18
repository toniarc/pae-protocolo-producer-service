package br.gov.pa.prodepa.pae.protocolo.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.pa.prodepa.pae.protocolo.application.service.ProtocoloApplicationService;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocolarDocumentoDto;

@RestController
@RequestMapping("/protocolos")
public class ProtocoloController {

	private final ProtocoloApplicationService service;
	
	@Autowired
	public ProtocoloController(ProtocoloApplicationService service) {
		super();
		this.service = service;
	}

	@PostMapping
	public void protocolarDococumento(@RequestBody ProtocolarDocumentoDto protocolarDocumentoDto) {
		service.protocolarDococumento(protocolarDocumentoDto);
	}
	
}
