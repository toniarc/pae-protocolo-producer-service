package br.gov.pa.prodepa.pae.protocolo.domain.port;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocolarDocumentoDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloDto;

public interface MessagingService {

	void sendMessage(ProtocoloDto protocoloDto);

	String enriquecerModeloDadosSuporte(ProtocolarDocumentoDto dto);

}
