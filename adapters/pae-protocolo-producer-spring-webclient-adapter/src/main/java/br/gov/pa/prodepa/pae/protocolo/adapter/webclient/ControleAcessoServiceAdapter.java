package br.gov.pa.prodepa.pae.protocolo.adapter.webclient;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.gov.pa.prodepa.pae.common.domain.dto.UsuarioDto;
import br.gov.pa.prodepa.pae.protocolo.domain.port.ControleAcessoService;

@Component
public class ControleAcessoServiceAdapter implements ControleAcessoService {

    @Override
    public List<UsuarioDto> buscarUsuarios(List<Long> assinantesIds) {
        //TODO Fazer integracao com o CA
        return assinantesIds.stream().map(id -> UsuarioDto.builder().id(id).build()).collect(Collectors.toList());
    }
    
}
