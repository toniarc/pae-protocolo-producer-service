package br.gov.pa.prodepa.pae.protocolo.domain.port;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloResponseDto;
import br.gov.pa.prodepa.pae.protocolo.domain.model.Protocolo;

public interface ProtocoloRepository {

	ProtocoloResponseDto salvar(Protocolo protocolo);

	Long buscarProximoSequencial(Integer ano);

	void criarSequencia(int ano, Long sequencial);

    boolean jaExisteSequenciaDeProtocoloIniciada();

}
