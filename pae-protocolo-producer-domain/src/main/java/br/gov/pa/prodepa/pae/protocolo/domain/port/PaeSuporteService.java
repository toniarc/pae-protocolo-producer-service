package br.gov.pa.prodepa.pae.protocolo.domain.port;

import java.util.List;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ConsultaPaginadaDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.AssuntoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.EspecieBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.LocalizacaoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.OrgaoPaeBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.SetorBasicDto;

public interface PaeSuporteService {

	ConsultaPaginadaDto<OrgaoPaeBasicDto> buscarOrgaoPorNomeOuSiglaOuCnpj(String nome, String sigla, String cpfCnpj,
			int pageSize, int pageNumber);

	ConsultaPaginadaDto<SetorBasicDto> buscarSetorPorNomeOuSigla(String nome, String sigla, int pageSize,
			int pageNumber);

	EspecieBasicDto buscarEspecie(Long especieId);

	AssuntoBasicDto buscarAssunto(Long assuntoId);

	List<OrgaoPaeBasicDto> buscarOrgaos(List<Long> orgaosIds);

	List<LocalizacaoBasicDto> buscarLocalizacoes(List<Long> localizacaoesIds);

    List<LocalizacaoBasicDto> buscarLocalizacoesUsuarioAtivas(Long id);

}
