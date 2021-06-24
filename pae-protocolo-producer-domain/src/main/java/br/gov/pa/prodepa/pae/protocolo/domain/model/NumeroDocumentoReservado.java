package br.gov.pa.prodepa.pae.protocolo.domain.model;

import java.util.Date;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.EspecieBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.LocalizacaoBasicDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class NumeroDocumentoReservado {

	private Integer id;
	private DocumentoProtocolado documentoProtocolado;
	
	private Integer ano;
	private EspecieBasicDto especie;
	private LocalizacaoBasicDto localizacao;
	private Long sequencial;
	
	private Long manutUsuarioId;
	private Date manutData;

	public NumeroDocumentoReservado() {
	}
	
	public NumeroDocumentoReservado(Integer id, Integer ano, Long especieId, Long localizacaoId,
			Long sequencial, Long documentoProtocoladoId) {
		super();
		this.id = id;
		this.ano = ano;
		this.especie = EspecieBasicDto.builder().id(especieId).build();
		this.localizacao = LocalizacaoBasicDto.builder().id(localizacaoId).build();
		this.sequencial = sequencial;
		this.documentoProtocolado = DocumentoProtocolado.builder().id(documentoProtocoladoId).build();
	}

	public NumeroDocumentoReservado(Integer id, Integer ano, Long especieId, Long localizacaoId, Long sequencial) {
		super();
		this.id = id;
		this.ano = ano;
		this.especie = EspecieBasicDto.builder().id(especieId).build();
		this.localizacao = LocalizacaoBasicDto.builder().id(localizacaoId).build();
		this.sequencial = sequencial;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NumeroDocumentoReservado other = (NumeroDocumentoReservado) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
