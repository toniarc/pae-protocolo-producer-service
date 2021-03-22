package br.gov.pa.prodepa.pae.protocolo.domain.port;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.suporte.client.PaeSuporteEnricherDto;

public interface MessagingService {

	void enviarParaFilaProtocolarDocumento(ProtocoloDto protocoloDto, String correlationId);

	PaeSuporteEnricherDto enriquecerModeloDadosSuporte(PaeSuporteEnricherDto enricher);

	//String enriquecerModeloDadosSuporte(ProtocolarDocumentoDto dto);

}
