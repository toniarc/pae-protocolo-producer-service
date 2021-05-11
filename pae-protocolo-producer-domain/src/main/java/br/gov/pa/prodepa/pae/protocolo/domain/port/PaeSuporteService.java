package br.gov.pa.prodepa.pae.protocolo.domain.port;

import br.gov.pa.prodepa.pae.common.domain.dto.OrgaoPaeBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ConsultaPaginadaDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.SetorBasicDto;

public interface PaeSuporteService {

	ConsultaPaginadaDto<OrgaoPaeBasicDto> buscarOrgaoPorNomeOuSiglaOuCnpj(String nome, String sigla, String cpfCnpj,
			int pageSize, int pageNumber);

	ConsultaPaginadaDto<SetorBasicDto> buscarSetorPorNomeOuSigla(String nome, String sigla, int pageSize,
			int pageNumber);

}
