package br.gov.pa.prodepa.pae.protocolo.domain.service;

import java.util.Arrays;
import java.util.List;

import br.gov.pa.prodepa.pae.common.domain.dto.ApplicationRoles;
import br.gov.pa.prodepa.pae.common.domain.dto.UsuarioDto;
import br.gov.pa.prodepa.pae.common.domain.exception.EntityNotFoundException;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.TipoDestinoDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.TipoEntradaSaidaProcesso;
import br.gov.pa.prodepa.pae.protocolo.domain.model.TipoDestino;
import br.gov.pa.prodepa.pae.protocolo.domain.port.PaeSuporteService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TipoDestinoDomainService implements TipoDestinoService {

	private final UsuarioDto usuarioLogado;
	
	private final PaeSuporteService suporteService;
	
	public List<TipoDestinoDto> listarTiposDeDestino(Long localizacaoId) {
		var buscarOrgaos = suporteService.buscarOrgaos(Arrays.asList(usuarioLogado.getOrgao().getId()));
		
		if(buscarOrgaos == null || buscarOrgaos.isEmpty()) {
			throw new EntityNotFoundException("Não foi encontrado nenhum órgão com o id " + usuarioLogado.getOrgao().getId());
		}
		
		var localizacoes = suporteService.buscarLocalizacoes(Arrays.asList(localizacaoId));
		
		if(localizacoes == null || localizacoes.isEmpty()) {
			throw new EntityNotFoundException("Não foi encontrada localização com o id " + localizacaoId);
		}
		
		var orgaoPaeBasicDto = buscarOrgaos.get(0);
		var localizacao = localizacoes.get(0);
		
		if(orgaoPaeBasicDto.getSaidaProcesso().equals(TipoEntradaSaidaProcesso.SOMENTE_SETOR_DE_PROTOCOLO.name())) { 
				if(Boolean.TRUE.equals(localizacao.getSetor().getProtocoladora()) 
						&& usuarioLogado.hasRole(ApplicationRoles.PROTOCOLISTA)) {
					return Arrays.asList(new TipoDestinoDto(TipoDestino.ORGAO),new TipoDestinoDto(TipoDestino.SETOR));
				} else {
					return Arrays.asList(new TipoDestinoDto(TipoDestino.SETOR));
				}
		} 
		
		return Arrays.asList(new TipoDestinoDto(TipoDestino.ORGAO),new TipoDestinoDto(TipoDestino.SETOR));
		
	}
}
