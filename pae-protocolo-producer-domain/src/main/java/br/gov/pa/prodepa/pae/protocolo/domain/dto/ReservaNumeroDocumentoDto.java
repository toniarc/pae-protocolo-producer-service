package br.gov.pa.prodepa.pae.protocolo.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservaNumeroDocumentoDto {

    private Long especieId; 
    private Long localizacaoId; 
    private String motivo;
}
