package br.gov.pa.prodepa.pae.protocolo.domain.port;

import br.gov.pa.prodepa.pae.documento.client.DocumentoBasicDto;

public interface PaeDocumentoService {

	DocumentoBasicDto buscarPorId(Long id);

}
