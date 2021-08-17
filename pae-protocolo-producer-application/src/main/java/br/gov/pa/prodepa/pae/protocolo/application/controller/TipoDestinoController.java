package br.gov.pa.prodepa.pae.protocolo.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.pa.prodepa.pae.protocolo.application.service.TipoDestinoApplicationService;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.TipoDestinoDto;

@RestController
@RequestMapping("/tipos-destinos")
public class TipoDestinoController {

	@Autowired
	private TipoDestinoApplicationService service;
	
	@GetMapping("/baseado-na-localizacao-e-orgao-usuario/{localizacaoId}")
	public List<TipoDestinoDto> listarTiposDeDestino(@PathVariable("localizacaoId") Long localizacaoId) {
		return service.listarTiposDeDestino(localizacaoId);
	}
}
