package br.gov.pa.prodepa.pae.protocolo.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.gov.pa.prodepa.pae.protocolo.domain.model.OrigemDocumento;
import br.gov.pa.prodepa.pae.protocolo.domain.model.Prioridade;
import br.gov.pa.prodepa.pae.protocolo.domain.model.TipoDestino;
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
	
	public List<Long> getOrgaosIds(){
		List<Long> orgaosIds = new ArrayList<Long>();
		orgaosIds.add(orgaoOrigemId);
		orgaosIds.addAll(interessadosOrgaosIds);
		
		if(tipoDestino.equals(TipoDestino.ORGAO)) {
			orgaosIds.addAll(destinosIds);
		}
		return orgaosIds;
	}
	
	public List<Long> getLocalizacoesIds() {
		List<Long> localizacaoesIds = new ArrayList<Long>();
		localizacaoesIds.addAll(interessadosLocalizacoesIds);
		localizacaoesIds.add(localizacaoOrigemId);
		
		if(tipoDestino.equals(TipoDestino.SETOR)) {
			localizacaoesIds.addAll(destinosIds);
		}
		return localizacaoesIds;
	}
	
}
