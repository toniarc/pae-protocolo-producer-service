package br.gov.pa.prodepa.pae.protocolo.domain.port;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.documento.DocumentoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.documento.ModeloEstruturaBasicDto;

public interface PaeDocumentoService {

	DocumentoBasicDto buscarDocumentoPorId(Long id);

	ModeloEstruturaBasicDto buscarModeloEstruturaPorId(Long id);

}
