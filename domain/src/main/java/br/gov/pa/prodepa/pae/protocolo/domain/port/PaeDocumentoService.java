package br.gov.pa.prodepa.pae.protocolo.domain.port;

import br.gov.pa.prodepa.pae.documento.client.DocumentoBasicDto;
import br.gov.pa.prodepa.pae.documento.client.ModeloEstruturaBasicDto;

public interface PaeDocumentoService {

	DocumentoBasicDto buscarDocumentoPorId(Long id);

	ModeloEstruturaBasicDto buscarModeloEstruturaPorId(Long id);

}
