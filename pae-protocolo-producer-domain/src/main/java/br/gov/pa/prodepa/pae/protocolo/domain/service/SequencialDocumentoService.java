package br.gov.pa.prodepa.pae.protocolo.domain.service;

import br.gov.pa.prodepa.pae.protocolo.domain.exception.SequencialDocumentoExistenteException;
import br.gov.pa.prodepa.pae.protocolo.domain.model.NumeroDocumentoReservado;
import br.gov.pa.prodepa.pae.protocolo.domain.model.SequencialDocumento;

public interface SequencialDocumentoService {

	SequencialDocumento buscarProximoSequencial(Long especieId, Long localizacaoId);

	SequencialDocumento criarNovaSequencia(Long especieId, Long localizacaoId) throws SequencialDocumentoExistenteException;

	NumeroDocumentoReservado reservarNumeroDocumento(Long especieId, Long localizacaoId);

}
