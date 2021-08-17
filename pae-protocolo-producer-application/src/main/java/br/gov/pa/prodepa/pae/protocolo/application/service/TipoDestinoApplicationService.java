package br.gov.pa.prodepa.pae.protocolo.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.TipoDestinoDto;
import br.gov.pa.prodepa.pae.protocolo.domain.service.TipoDestinoService;

@Component
public class TipoDestinoApplicationService {

	@Autowired
	private TipoDestinoService service;
	
	public List<TipoDestinoDto> listarTiposDeDestino(Long localizacaoId) {
		return service.listarTiposDeDestino(localizacaoId);
	}
}
