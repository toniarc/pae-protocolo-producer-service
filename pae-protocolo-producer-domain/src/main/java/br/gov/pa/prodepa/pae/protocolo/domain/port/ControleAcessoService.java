package br.gov.pa.prodepa.pae.protocolo.domain.port;

import java.util.List;

import br.gov.pa.prodepa.pae.common.domain.dto.UsuarioDto;

public interface ControleAcessoService {

    List<UsuarioDto> buscarUsuarios(List<Long> assinantesIds);

}
