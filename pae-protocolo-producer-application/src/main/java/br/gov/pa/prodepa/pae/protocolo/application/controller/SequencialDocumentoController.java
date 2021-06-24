package br.gov.pa.prodepa.pae.protocolo.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.pa.prodepa.pae.protocolo.application.service.SequencialDocumentoApplicationService;
import br.gov.pa.prodepa.pae.protocolo.domain.model.NumeroDocumentoReservado;

@RestController
@RequestMapping("/numeros-sequenciais-reservados")
public class SequencialDocumentoController {

	private SequencialDocumentoApplicationService service;

	@Autowired
	public SequencialDocumentoController(SequencialDocumentoApplicationService service) {
		this.service = service;
	}
	
	@PostMapping("/especie/{especieId}/localizacao/{localizacaoId}")
	public NumeroDocumentoReservado reservarNumeroDocumento(@PathVariable("especieId") Long especieId, @PathVariable("localizacaoId") Long localizacaoId) {
		return service.reservarNumeroDocumento(especieId, localizacaoId);
	}

	@GetMapping("/especie/{especieId}/localizacao/{localizacaoId}")
	public List<NumeroDocumentoReservado> listarNumerosReservados(@PathVariable("especieId") Long especieId, @PathVariable("localizacaoId") Long localizacaoId) {
		return service.listarNumerosReservados(especieId, localizacaoId);
	}
}
