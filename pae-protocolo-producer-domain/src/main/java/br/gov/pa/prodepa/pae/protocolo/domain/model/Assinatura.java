package br.gov.pa.prodepa.pae.protocolo.domain.model;

import java.util.Date;

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
public class Assinatura {

	private int id;
	private Anexo anexo;
	private Long usuarioAssinanteId;
	private String tipoAssinatura;
	private Date dataRequisicao;
	private Long usuarioRequisitanteId;
	private byte[] dadosAssinatura;
	private Boolean assinado;
}
