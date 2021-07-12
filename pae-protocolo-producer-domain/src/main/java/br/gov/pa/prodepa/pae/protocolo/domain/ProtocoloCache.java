package br.gov.pa.prodepa.pae.protocolo.domain;

import java.util.List;
import java.util.stream.Collectors;

import br.gov.pa.prodepa.pae.common.domain.dto.UsuarioDto;
import br.gov.pa.prodepa.pae.common.domain.exception.EntityNotFoundException;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocolarDocumentoDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.documento.DocumentoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.MunicipioBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.PessoaFisicaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.PessoaJuridicaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.AssuntoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.EspecieBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.LocalizacaoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.OrgaoPaeBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.port.PaeDocumentoService;
import br.gov.pa.prodepa.pae.protocolo.domain.port.PaeSuporteService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProtocoloCache {

    EspecieBasicDto especie;
    AssuntoBasicDto assunto;
    List<OrgaoPaeBasicDto> orgaos;
    List<LocalizacaoBasicDto> localizacoes;
    List<LocalizacaoBasicDto> localizacoesUsuario;
    DocumentoBasicDto documento;

    List<PessoaFisicaBasicDto> pessoasFisicas;
    List<PessoaJuridicaBasicDto> pessoasJuridicas;
    MunicipioBasicDto municipio;

    public ProtocoloCache(PaeSuporteService suporteService, PaeDocumentoService documentoService, ProtocolarDocumentoDto dto, UsuarioDto usuarioLogado){
        especie = suporteService.buscarEspecie(dto.getEspecieId());
		assunto = suporteService.buscarAssunto(dto.getAssuntoId());
		orgaos = suporteService.buscarOrgaos(dto.getOrgaosIds());
		localizacoes = suporteService.buscarLocalizacoes(dto.getLocalizacoesIds());
		localizacoesUsuario = suporteService.buscarLocalizacoesUsuarioAtivas(usuarioLogado.getId());

        documento = documentoService.buscarDocumentoPorId(dto.getDocumentoId());
		documento.getModeloConteudo()
				.setModeloEstrutura(documentoService.buscarModeloEstruturaPorId(documento.getModeloConteudo().getId()));
    }

    public PessoaFisicaBasicDto getPessoaFisica(Long id){
        return pessoasFisicas.stream()
            .filter(pf -> pf.getId().equals(id))
            .findFirst()
            .orElseThrow( () -> new EntityNotFoundException("Não foi encontrado nenhuma pessoa física com o id " + id));
    }

    public PessoaJuridicaBasicDto getPessoaJuridica(Long id){
        return pessoasJuridicas.stream()
            .filter(pf -> pf.getId().equals(id))
            .findFirst()
            .orElseThrow( () -> new EntityNotFoundException("Não foi encontrado nenhuma pessoa jurídica com o id " + id));
    }

    public List<OrgaoPaeBasicDto> getOrgaos(List<Long> ids) {
		return orgaos.stream()
            .filter(a -> ids.contains(a.getId()))
            .collect(Collectors.toList());
	}

    public OrgaoPaeBasicDto getOrgao(Long id) {
		return orgaos.stream()
            .filter(a -> a.getId().equals(id))
            .findFirst()
            .orElseThrow( () -> new EntityNotFoundException("Não foi encontrado nenhum órgão com o id " + id));
	}

	public LocalizacaoBasicDto getLocalizacao(Long id) {
		return localizacoes.stream()
            .filter(a -> a.getId().equals(id))
            .findFirst()
            .orElseThrow( () -> new EntityNotFoundException("Não foi encontrado nenhuma localização com o id " + id));
	}

    public List<LocalizacaoBasicDto> getLocalizacoes(List<Long> ids) {
		return localizacoes.stream()
            .filter(a -> ids.contains(a.getId()))
            .collect(Collectors.toList());
	}
}
