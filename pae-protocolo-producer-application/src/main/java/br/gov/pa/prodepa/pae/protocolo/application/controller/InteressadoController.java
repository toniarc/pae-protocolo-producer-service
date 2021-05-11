package br.gov.pa.prodepa.pae.protocolo.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ConsultaPaginadaDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.InteressadoDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.InteressadoSearchDto;
import br.gov.pa.prodepa.pae.protocolo.domain.model.TipoInteressado;
import br.gov.pa.prodepa.pae.protocolo.domain.service.InteressadoService;

@RestController
@RequestMapping("/interessados")
public class InteressadoController {

	private final InteressadoService service;

	@Autowired
	public InteressadoController(InteressadoService service) {
		super();
		this.service = service;
	}
	
	@GetMapping
	public ConsultaPaginadaDto<InteressadoDto> buscarInteressados(
			@RequestParam(value="cpfCnpj", required = false) String cpfCnpj,
			@RequestParam(value="nome", required = false) String nome, 
			@RequestParam(value="sigla", required = false) String sigla, 
			@RequestParam(value="tipoInteressado", required = false) TipoInteressado tipoInteressado, 
			@RequestParam(value="pageSize", required = true) int pageSize, 
			@RequestParam(value="pageNumber", required = true) int pageNumber){
		InteressadoSearchDto dto = new InteressadoSearchDto();
		dto.setCpfCnpj(cpfCnpj);
		dto.setNome(nome);
		dto.setSigla(sigla);
		dto.setTipoInteressado(tipoInteressado);
		dto.setPageNumber(pageNumber);
		dto.setPageSize(pageSize);
		return service.buscarInteressado(dto);
	}
}
