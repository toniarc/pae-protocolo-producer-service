package br.gov.pa.prodepa.pae.protocolo.domain.dto;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.LocalizacaoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.OrgaoPaeBasicDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DestinoProtocoloDto {

    private OrgaoPaeBasicDto orgaoDestino;
	private LocalizacaoBasicDto localizacaoDestino;

}
