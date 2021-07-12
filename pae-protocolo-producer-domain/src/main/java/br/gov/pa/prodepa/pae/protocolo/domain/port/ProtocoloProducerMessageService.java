package br.gov.pa.prodepa.pae.protocolo.domain.port;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.domain.model.DocumentoProtocolado;

public interface ProtocoloProducerMessageService {

	void enviarParaFilaProtocolarDocumento(ProtocoloDto protocoloDto, String string);

    void enviarParaFilaDaCaixaDeEntrada(DocumentoProtocolado documentoProtocolado, String correlationId);

}
