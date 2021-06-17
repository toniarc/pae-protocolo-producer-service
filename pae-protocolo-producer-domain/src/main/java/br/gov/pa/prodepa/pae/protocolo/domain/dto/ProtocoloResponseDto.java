package br.gov.pa.prodepa.pae.protocolo.domain.dto;

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
public class ProtocoloResponseDto {

	private Integer ano;
	private Long numero;
	private Long usuarioId;
	private String fileStorageId;
	private String successMessage;
	private String detailMessage;
}
