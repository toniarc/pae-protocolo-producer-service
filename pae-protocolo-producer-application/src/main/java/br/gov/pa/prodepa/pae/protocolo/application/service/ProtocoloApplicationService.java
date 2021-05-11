package br.gov.pa.prodepa.pae.protocolo.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocolarDocumentoDto;
import br.gov.pa.prodepa.pae.protocolo.domain.service.ProtocoloService;

@Component
public class ProtocoloApplicationService {

	private final ProtocoloService protocoloService;
	
	@Autowired
	public ProtocoloApplicationService(ProtocoloService protocoloService) {
		super();
		this.protocoloService = protocoloService;
	}

	@Transactional(rollbackFor = Throwable.class)
	public void protocolarDococumento(ProtocolarDocumentoDto dto) {
		protocoloService.protocolarDocumento(dto);
	}
	
}