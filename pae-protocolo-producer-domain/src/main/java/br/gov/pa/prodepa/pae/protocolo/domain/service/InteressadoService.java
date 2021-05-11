package br.gov.pa.prodepa.pae.protocolo.domain.service;

import java.util.List;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ConsultaPaginadaDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.InteressadoDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.InteressadoSearchDto;

public interface InteressadoService {

	ConsultaPaginadaDto<InteressadoDto> buscarInteressado(InteressadoSearchDto searchDto);

}
