package br.gov.pa.prodepa.pae.protocolo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProtocoloId {
	private Integer anoProtocolo;
	private Long numeroProtocolo;
}
