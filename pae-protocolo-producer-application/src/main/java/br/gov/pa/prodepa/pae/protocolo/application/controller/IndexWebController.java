package br.gov.pa.prodepa.pae.protocolo.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexWebController {

	@GetMapping("/index")
	public String greeting() {
		return "index";
	}

}

