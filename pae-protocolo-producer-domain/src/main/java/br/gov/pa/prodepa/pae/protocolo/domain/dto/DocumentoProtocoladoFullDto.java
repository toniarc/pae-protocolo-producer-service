package br.gov.pa.prodepa.pae.protocolo.domain.dto;

import java.util.Date;
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
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DocumentoProtocoladoFullDto {

	private Long id;

	private Integer anoDocumento;
	private Long numeroDocumento;

	private EspecieBasicDto especie;
	private AssuntoBasicDto assunto;
	private MunicipioBasicDto municipio;
	private DocumentoBasicDto documento;
	
	private OrigemDocumento origemDocumento;
	private String complemento;
	private Prioridade prioridade;
	private TipoDestino tipoDestino;
	private List<UsuarioDto> usuariosQueDevemAssinar;
	private List<UsuarioDto> usuariosQueJaAssinaram;

	private List<DestinoProtocoloDto> destinos;

	private String conteudoDocumento;
	private Long modeloConteudoId;
	private List<PessoaFisicaBasicDto> pessoasFisicasInteressadas;
	private List<PessoaJuridicaBasicDto> pessoasJuridicasInteressadas;
	private List<OrgaoPaeBasicDto> orgaosInteressados;
	private List<LocalizacaoBasicDto> localizacoesInteressadas;
	private LocalizacaoBasicDto localizacaoOrigem;
	private OrgaoPaeBasicDto orgaoOrigem;
	
	private Boolean jaFoiTramitado;

	private Long criadoPor;
	private Date criadoEm;

	private Long atualizadoPor;
	private Date atualizadoEm;
}
