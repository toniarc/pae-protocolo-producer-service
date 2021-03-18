package br.gov.pa.prodepa.pae.protocolo.domain.dto;

import java.util.List;

import br.gov.pa.prodepa.nucleopa.client.MunicipioBasicDto;
import br.gov.pa.prodepa.nucleopa.client.PessoaFisicaBasicDto;
import br.gov.pa.prodepa.nucleopa.client.PessoaJuridicaBasicDto;
import br.gov.pa.prodepa.pae.documento.client.DocumentoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.model.OrigemDocumento;
import br.gov.pa.prodepa.pae.protocolo.domain.model.Prioridade;
import br.gov.pa.prodepa.pae.protocolo.domain.model.TipoDestino;
import br.gov.pa.prodepa.pae.protocolo.domain.model.Usuario;
import br.gov.pa.prodepa.pae.suporte.client.AssuntoBasicDto;
import br.gov.pa.prodepa.pae.suporte.client.EspecieBasicDto;
import br.gov.pa.prodepa.pae.suporte.client.LocalizacaoBasicDto;
import br.gov.pa.prodepa.pae.suporte.client.OrgaoPaeBasicDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolarDocumentoEnriquecoDto {

	private EspecieBasicDto especie;
	private AssuntoBasicDto assunto;
	private MunicipioBasicDto municipio;
	private DocumentoBasicDto documento;
	private OrigemDocumento origemDocumento;
	private String complemento;
	private Prioridade prioridade;
	
	//assinantes
	private List<Usuario> assinantess;
	
	//interessados
	private List<PessoaFisicaBasicDto> interessadosPessoasFisicas;
	private List<PessoaJuridicaBasicDto> interessadosPessoasJuricas;
	private List<OrgaoPaeBasicDto> orgaosInteressados;
	private List<LocalizacaoBasicDto> localizacoesInteressadas;
	
	//origem
	private LocalizacaoBasicDto localizacaoOrigem;
	private OrgaoPaeBasicDto orgaoOrigem;
	
	//destinos
	private TipoDestino tipoDestino;
	private List<OrgaoPaeBasicDto> orgaosDestinos;
	private List<LocalizacaoBasicDto> localizacoesDestino;
		
	private Long numeroReservadoId;
}
