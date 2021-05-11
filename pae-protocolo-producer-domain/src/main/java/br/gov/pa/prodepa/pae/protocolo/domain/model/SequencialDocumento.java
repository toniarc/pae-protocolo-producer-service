package br.gov.pa.prodepa.pae.protocolo.domain.model;
// Generated 11 de mar de 2021 14:36:31 by Hibernate Tools 5.4.27.Final

import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.EspecieBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.LocalizacaoBasicDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SequencialDocumento {

	private Integer ano;
	private EspecieBasicDto especie;
	private LocalizacaoBasicDto localizacao;
	private Long sequencial;

	public SequencialDocumento() {
	}

}
