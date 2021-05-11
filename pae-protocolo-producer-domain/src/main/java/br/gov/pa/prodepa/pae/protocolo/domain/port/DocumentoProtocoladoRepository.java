package br.gov.pa.prodepa.pae.protocolo.domain.port;

import br.gov.pa.prodepa.pae.protocolo.domain.model.DocumentoProtocolado;

public interface DocumentoProtocoladoRepository {

	DocumentoProtocolado salvar(DocumentoProtocolado dp);

}
