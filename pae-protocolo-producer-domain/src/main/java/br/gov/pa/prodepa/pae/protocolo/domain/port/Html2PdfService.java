package br.gov.pa.prodepa.pae.protocolo.domain.port;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.documento.ModeloEstruturaBasicDto;

public interface Html2PdfService {

	byte[] gerarPdf(String conteudo, ModeloEstruturaBasicDto me);
}
