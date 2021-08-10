package br.gov.pa.prodepa.pae.protocolo.domain.port;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.DocumentoProtocoladoFullDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloDto;

public interface ProtocoloProducerMessageService {

	void enviarParaFilaProtocolarDocumento(ProtocoloDto protocoloDto, String string);

    void enviarParaFilaDaCaixaDeEntrada(DocumentoProtocoladoFullDto documentoProtocolado, String correlationId);

}
