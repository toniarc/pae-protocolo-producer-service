package br.gov.pa.prodepa.pae.protocolo.domain.service;

import java.util.List;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.TipoDestinoDto;

public interface TipoDestinoService {

	List<TipoDestinoDto> listarTiposDeDestino(Long localizacaoId);
}
