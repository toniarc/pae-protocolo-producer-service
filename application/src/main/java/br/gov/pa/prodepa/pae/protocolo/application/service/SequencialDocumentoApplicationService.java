package br.gov.pa.prodepa.pae.protocolo.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pa.prodepa.pae.protocolo.domain.model.NumeroDocumentoReservado;
import br.gov.pa.prodepa.pae.protocolo.domain.service.SequencialDocumentoService;

@Component
public class SequencialDocumentoApplicationService {

	private final SequencialDocumentoService service;

	@Autowired
	public SequencialDocumentoApplicationService(SequencialDocumentoService service) {
		this.service = service;
	}
	
	public NumeroDocumentoReservado reservarNumeroDocumento(Long especieId, Long localizacaoId) {
		return service.reservarNumeroDocumento(especieId, localizacaoId);
	}

}
