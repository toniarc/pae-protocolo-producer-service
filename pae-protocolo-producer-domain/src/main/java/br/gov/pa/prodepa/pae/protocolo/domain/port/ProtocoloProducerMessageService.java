package br.gov.pa.prodepa.pae.protocolo.domain.port;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloDto;

public interface ProtocoloProducerMessageService {

	void enviarParaFilaProtocolarDocumento(ProtocoloDto protocoloDto, String string);

}
