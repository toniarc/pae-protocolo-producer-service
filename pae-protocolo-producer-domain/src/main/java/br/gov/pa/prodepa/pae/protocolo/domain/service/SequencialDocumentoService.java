package br.gov.pa.prodepa.pae.protocolo.domain.service;

import java.util.List;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ReservaNumeroDocumentoDto;
import br.gov.pa.prodepa.pae.protocolo.domain.exception.SequencialDocumentoExistenteException;
import br.gov.pa.prodepa.pae.protocolo.domain.model.NumeroDocumentoReservado;
import br.gov.pa.prodepa.pae.protocolo.domain.model.SequencialDocumento;

public interface SequencialDocumentoService {

	SequencialDocumento buscarProximoSequencial(Long especieId, Long localizacaoId);

	SequencialDocumento criarNovaSequencia(Long especieId, Long localizacaoId) throws SequencialDocumentoExistenteException;

	NumeroDocumentoReservado reservarNumeroDocumento(ReservaNumeroDocumentoDto dto);

    List<NumeroDocumentoReservado> listarNumerosReservados(Long especieId, Long localizacaoId);

}
