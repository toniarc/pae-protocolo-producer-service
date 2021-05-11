package br.gov.pa.prodepa.pae.protocolo.domain.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.gov.pa.prodepa.pae.common.domain.dto.UsuarioDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.documento.DocumentoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.documento.DocumentoProtocoladoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.MunicipioBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.PessoaFisicaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.PessoaJuridicaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.AssuntoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.EspecieBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.LocalizacaoComEnderecoDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.OrgaoPaeBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.model.OrigemDocumento;
import br.gov.pa.prodepa.pae.protocolo.domain.model.Prioridade;
import br.gov.pa.prodepa.pae.protocolo.domain.model.TipoDestino;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProtocoloDto implements Serializable {
	
	private static final long serialVersionUID = 2328859682092032647L;
	
	private Integer anoProtocolo;
	private Long numeroProtocolo;
	private Date dataProtocolo;
	
	private EspecieBasicDto especie;
	private AssuntoBasicDto assunto;
	private MunicipioBasicDto municipio;
	private OrigemDocumento origemDocumento;
	private String complemento;
	private Prioridade prioridade;
	private DocumentoBasicDto documento;
	private DocumentoProtocoladoBasicDto documentoProtocolado;
	private OrgaoPaeBasicDto orgaoDestino;
	private LocalizacaoComEnderecoDto localizacaoDestino;
	
	private UsuarioDto usuarioCadastro;
	
	private OrgaoPaeBasicDto orgaoOrigem;
	private LocalizacaoComEnderecoDto localizacaoOrigem;
	
	//interessados
	private List<PessoaFisicaBasicDto> interessadosPessoasFisicas;
	private List<PessoaJuridicaBasicDto> interessadosPessoasJuricas;
	private List<OrgaoPaeBasicDto> orgaosInteressados;
	private List<LocalizacaoComEnderecoDto> localizacoesInteressadas;
	
	private List<UsuarioDto> assinantes;
	
	private TipoDestino tipoDestino;

}
