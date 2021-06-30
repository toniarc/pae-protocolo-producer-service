package br.gov.pa.prodepa.pae.protocolo.application.service;

import org.springframework.stereotype.Component;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.domain.service.ProtocoloService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ProtocoloApplicationService {

	private final ProtocoloService protocoloService;
	
	public void protocolarDocumento(ProtocoloDto dto) {
		protocoloService.protocolarDocumento(dto);
	}

	public boolean jaExisteSequenciaDeProtocoloIniciada() {
		return protocoloService.jaExisteSequenciaDeProtocoloIniciada();
	}
}
