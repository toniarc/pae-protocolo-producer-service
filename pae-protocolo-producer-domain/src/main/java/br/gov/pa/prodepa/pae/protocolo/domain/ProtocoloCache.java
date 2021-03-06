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
import br.gov.pa.prodepa.pae.protocolo.domain.port.ControleAcessoService;
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
    List<UsuarioDto> usuarios;

    public ProtocoloCache(PaeSuporteService suporteService, PaeDocumentoService documentoService, ControleAcessoService controleAcessoService, ProtocolarDocumentoDto dto, UsuarioDto usuarioLogado){
        especie = suporteService.buscarEspecie(dto.getEspecieId());
		assunto = suporteService.buscarAssunto(dto.getAssuntoId());
		orgaos = suporteService.buscarOrgaos(dto.getOrgaosIds());
		localizacoes = suporteService.buscarLocalizacoes(dto.getLocalizacoesIds());
		localizacoesUsuario = suporteService.buscarLocalizacoesUsuarioAtivas(usuarioLogado.getId());

        documento = documentoService.buscarDocumentoPorId(dto.getDocumentoId());
		documento.getModeloConteudo()
				.setModeloEstrutura(documentoService.buscarModeloEstruturaPorId(documento.getModeloConteudo().getId()));

        usuarios = controleAcessoService.buscarUsuarios(dto.getAssinantesIds());
    }

    public PessoaFisicaBasicDto getPessoaFisica(Long id){
        return pessoasFisicas.stream()
            .filter(pf -> pf.getId().equals(id))
            .findFirst()
            .orElseThrow( () -> new EntityNotFoundException("N??o foi encontrado nenhuma pessoa f??sica com o id " + id));
    }

    public PessoaJuridicaBasicDto getPessoaJuridica(Long id){
        return pessoasJuridicas.stream()
            .filter(pf -> pf.getId().equals(id))
            .findFirst()
            .orElseThrow( () -> new EntityNotFoundException("N??o foi encontrado nenhuma pessoa jur??dica com o id " + id));
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
            .orElseThrow( () -> new EntityNotFoundException("N??o foi encontrado nenhum ??rg??o com o id " + id));
	}

	public LocalizacaoBasicDto getLocalizacao(Long id) {
		return localizacoes.stream()
            .filter(a -> a.getId().equals(id))
            .findFirst()
            .orElseThrow( () -> new EntityNotFoundException("N??o foi encontrado nenhuma localiza????o com o id " + id));
	}

    public List<LocalizacaoBasicDto> getLocalizacoes(List<Long> ids) {
		return localizacoes.stream()
            .filter(a -> ids.contains(a.getId()))
            .collect(Collectors.toList());
	}

    public List<UsuarioDto> getUsuarios(List<Long> ids) {
        return usuarios.stream()
            .filter(a -> ids.contains(a.getId()))
            .collect(Collectors.toList());
    }

    public List<PessoaFisicaBasicDto> getPessoasFisicas(List<Long> ids) {
        return pessoasFisicas.stream()
            .filter(a -> ids.contains(a.getId()))
            .collect(Collectors.toList());
    }

    public List<PessoaJuridicaBasicDto> getPessoasJuridicas(List<Long> ids) {
        return pessoasJuridicas.stream()
            .filter(a -> ids.contains(a.getId()))
            .collect(Collectors.toList());
    }
}
