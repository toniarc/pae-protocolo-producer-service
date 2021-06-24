package br.gov.pa.prodepa.pae.protocolo.domain.port;

import java.util.List;

import br.gov.pa.prodepa.pae.protocolo.domain.exception.SequencialDocumentoExistenteException;
import br.gov.pa.prodepa.pae.protocolo.domain.model.NumeroDocumentoReservado;

public interface SequencialDocumentoRepository {

	Long buscarProximoSequencial(Integer ano, Long especieId, Long localizacaoId);

	void criarSequencia(Integer ano, Long especieId, Long localizacaoId, Long sequenciaAtual) throws SequencialDocumentoExistenteException;

	NumeroDocumentoReservado salvar(NumeroDocumentoReservado reserva);

	NumeroDocumentoReservado buscarNumeroReservado(Long numeroReservadoId);

    List<NumeroDocumentoReservado> listarNumerosReservados(Long especieId, Long localizacaoId);

}
