package br.gov.pa.prodepa.pae.protocolo.domain.dto;

import java.io.Serializable;
import java.util.List;

import br.gov.pa.prodepa.pae.protocolo.client.dto.OrigemDocumento;
import br.gov.pa.prodepa.pae.protocolo.client.dto.Prioridade;
import br.gov.pa.prodepa.pae.protocolo.client.dto.TipoDestino;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProtocolarDocumentoDto implements Serializable {

	private static final long serialVersionUID = -492212626768848048L;

	private Long especieId;
	private Long assuntoId;
	private Long municipioId;
	private Long documentoId;
	private OrigemDocumento origemDocumento;
	private String complemento;
	private Prioridade prioridade;
	
	//assinantes
	private List<Long> assinantesIds;
	
	//interessados
	private List<Long> interessadosPessoasFisicasIds;
	private List<Long> interessadosPessoasJuridicasIds;
	private List<Long> interessadosOrgaosIds;
	private List<Long> interessadosLocalizacoesIds;
	
	//origem
	private Long localizacaoOrigemId;
	private Long orgaoOrigemId;
	
	//destinos
	private TipoDestino tipoDestino;
	private List<Long> destinosIds;
		
	private Long numeroReservadoId;
	
}
