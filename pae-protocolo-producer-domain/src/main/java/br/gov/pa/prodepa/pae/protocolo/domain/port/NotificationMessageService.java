package br.gov.pa.prodepa.pae.protocolo.domain.port;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloResponseDto;

public interface NotificationMessageService {

	void send(ProtocoloResponseDto protocoloSalvo);

}
