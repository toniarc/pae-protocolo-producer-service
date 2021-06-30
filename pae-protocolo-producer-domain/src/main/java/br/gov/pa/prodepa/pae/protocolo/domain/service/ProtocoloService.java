package br.gov.pa.prodepa.pae.protocolo.domain.service;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloDto;

public interface ProtocoloService {

	void protocolarDocumento(ProtocoloDto protocoloDto);

    boolean jaExisteSequenciaDeProtocoloIniciada();

}
