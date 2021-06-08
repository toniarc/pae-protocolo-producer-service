package br.gov.pa.prodepa.pae.protocolo.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import br.gov.pa.prodepa.pae.common.domain.exception.DomainException;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ConsultaPaginadaDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.InteressadoDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.InteressadoSearchDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.PessoaFisicaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.nucleopa.PessoaJuridicaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.OrgaoPaeBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.SetorBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.model.TipoInteressado;
import br.gov.pa.prodepa.pae.protocolo.domain.port.NucleopaRestClient;
import br.gov.pa.prodepa.pae.protocolo.domain.port.PaeSuporteService;

public class InteressadoDomainService implements InteressadoService{

	private final NucleopaRestClient nucleopaClient;
	private final PaeSuporteService paeSuporteClient;

	public InteressadoDomainService(NucleopaRestClient nucleopaClient, PaeSuporteService paeSuporteClient) {
		this.nucleopaClient = nucleopaClient;
		this.paeSuporteClient = paeSuporteClient;
	}
	
	@Override
	public ConsultaPaginadaDto<InteressadoDto> buscarInteressado(InteressadoSearchDto searchDto) {
		
		validarCamposObrigatorios(searchDto);
		
		if(searchDto.getTipoInteressado().equals(TipoInteressado.PESSOA_FISICA)) {
			ConsultaPaginadaDto<PessoaFisicaBasicDto> consulta = nucleopaClient.buscarPessoaFisicaPorNomeOuCpf(searchDto.getNome(), searchDto.getCpfCnpj(), searchDto.getPageSize(), searchDto.getPageNumber());
			
			List<InteressadoDto> list = consulta.getContent().stream()
			.map( pf -> new InteressadoDto(pf))
			.collect(Collectors.toList());
			
			return new ConsultaPaginadaDto<InteressadoDto>(consulta.getTotalPages(), consulta.getTotalElements(), consulta.getCurrentPage(), list);
		}
		
		
		if(searchDto.getTipoInteressado().equals(TipoInteressado.PESSOA_JURIDICA)) {
			ConsultaPaginadaDto<PessoaJuridicaBasicDto> consulta = nucleopaClient.buscarPessoaJuridicaPorNomeOuCnpj(searchDto.getNome(), searchDto.getCpfCnpj(), searchDto.getPageSize(), searchDto.getPageNumber());
			
			List<InteressadoDto> list = consulta.getContent().stream()
			.map( pj -> new InteressadoDto(pj))
			.collect(Collectors.toList());
			
			return new ConsultaPaginadaDto<InteressadoDto>(consulta.getTotalPages(), consulta.getTotalElements(), consulta.getCurrentPage(), list);
		}
		
		
		if(searchDto.getTipoInteressado().equals(TipoInteressado.ORGAO)) {
			ConsultaPaginadaDto<OrgaoPaeBasicDto> consulta = paeSuporteClient.buscarOrgaoPorNomeOuSiglaOuCnpj(searchDto.getNome(), searchDto.getSigla(), searchDto.getCpfCnpj(), searchDto.getPageSize(), searchDto.getPageNumber());
			
			List<InteressadoDto> list = consulta.getContent().stream()
			.map( pj -> new InteressadoDto(pj))
			.collect(Collectors.toList());
			
			return new ConsultaPaginadaDto<InteressadoDto>(consulta.getTotalPages(), consulta.getTotalElements(), consulta.getCurrentPage(), list);
		}
		
		if(searchDto.getTipoInteressado().equals(TipoInteressado.SETOR)) {
			ConsultaPaginadaDto<SetorBasicDto> consulta = paeSuporteClient.buscarSetorPorNomeOuSigla(searchDto.getNome(), searchDto.getSigla(), searchDto.getPageSize(), searchDto.getPageNumber());
			
			List<InteressadoDto> list = consulta.getContent().stream()
			.map( s -> new InteressadoDto(s))
			.collect(Collectors.toList());
			
			return new ConsultaPaginadaDto<InteressadoDto>(consulta.getTotalPages(), consulta.getTotalElements(), consulta.getCurrentPage(), list);
		}
		
		return new ConsultaPaginadaDto<InteressadoDto>();
		
	}

	private void validarCamposObrigatorios(InteressadoSearchDto searchDto) {
		DomainException de = new DomainException();
		
		if(searchDto.getTipoInteressado() == null) {
			de.addError("Informe o tipo de interessado");
		}
		
		if(searchDto.getTipoInteressado() != null && 
				searchDto.getTipoInteressado().equals(TipoInteressado.PESSOA_FISICA) &&
				(searchDto.getNome() == null || searchDto.getNome().trim().length() == 0) && 
				(searchDto.getCpfCnpj() == null || searchDto.getCpfCnpj().trim().length() == 0)) {
			de.addError("Informe ao menos um dos seguintes campos: nome ou cpf");
		}
		
		if(searchDto.getTipoInteressado() != null && 
				searchDto.getTipoInteressado().equals(TipoInteressado.PESSOA_JURIDICA) &&
				(searchDto.getNome() == null || searchDto.getNome().trim().length() == 0) && 
				(searchDto.getCpfCnpj() == null || searchDto.getCpfCnpj().trim().length() == 0)) {
			de.addError("Informe ao menos um dos seguintes campos: nome ou cnpj");
		}
		
		if(searchDto.getTipoInteressado() != null && 
				searchDto.getTipoInteressado().equals(TipoInteressado.ORGAO) &&
				(searchDto.getNome() == null || searchDto.getNome().trim().length() == 0) && 
				(searchDto.getCpfCnpj() == null || searchDto.getCpfCnpj().trim().length() == 0) &&
				(searchDto.getSigla() == null || searchDto.getSigla().trim().length() == 0)) {
			de.addError("Informe ao menos um dos seguintes campos: nome, sigla ou cnpj");
		}
		
		if(searchDto.getTipoInteressado() != null && 
				searchDto.getTipoInteressado().equals(TipoInteressado.SETOR) &&
				(searchDto.getNome() == null || searchDto.getNome().trim().length() == 0) && 
				(searchDto.getSigla() == null || searchDto.getSigla().trim().length() == 0)) {
			de.addError("Informe ao menos um dos seguintes campos: nome ou sigla");
		}
		
		de.throwException();
		
	}
	
}
