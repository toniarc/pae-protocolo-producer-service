package br.gov.pa.prodepa.pae.protocolo.domain.dto;

import java.util.List;

import br.gov.pa.prodepa.pae.common.domain.dto.UsuarioDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.documento.DocumentoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.MunicipioBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.PessoaFisicaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.PessoaJuridicaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.AssuntoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.EspecieBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.LocalizacaoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.OrgaoPaeBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.model.OrigemDocumento;
import br.gov.pa.prodepa.pae.protocolo.domain.model.Prioridade;
import br.gov.pa.prodepa.pae.protocolo.domain.model.TipoDestino;
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
	private List<UsuarioDto> assinantess;
	
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
