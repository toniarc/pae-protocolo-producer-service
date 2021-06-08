package br.gov.pa.prodepa.pae.protocolo.domain.validator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.gov.pa.prodepa.pae.common.domain.exception.DomainException;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocolarDocumentoDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.AssuntoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.EspecieBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.LocalizacaoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte.OrgaoPaeBasicDto;
import br.gov.pa.prodepa.pae.protocolo.domain.model.TipoDestino;

public class ProtocoloValidator {

	DomainException de = new DomainException();
	private ProtocolarDocumentoDto dto;
	
	public ProtocoloValidator(ProtocolarDocumentoDto dto) {
		this.dto = dto;
	}

	public static ProtocoloValidator getInstance(ProtocolarDocumentoDto dto) {
		return new ProtocoloValidator(dto);
	}

	public ProtocoloValidator validar() {
		de.throwException();
		return this;
	}

	public ProtocoloValidator validarCamposDinamicosUsadosNoDocumento(String conteudo, TipoDestino tipoDestino) {
		
		Pattern pattern = Pattern.compile("(%24%7B([A-Z_]*)%7D)");
		Matcher matcher = pattern.matcher(conteudo);
		
		while(matcher.find()) {
			String campoDinamico = matcher.group(2);
			
			if(!campoDinamico.equals("DATA_PROTOCOLO") && !campoDinamico.equals("NUMERO_PROTOCOLO")) {
				
				if(!tipoDestino.equals(TipoDestino.ORGAO)) {
					
					if(campoDinamico.trim().equals("ORGAO_ORIGEM_NOME") ) {
						de.addError("O campo dinamico 'ORGAO_ORIGEM_NOME' so pode ser usado quando o tipo de destino for 'SETOR'");
					}

					if(campoDinamico.trim().equals("ORGAO_DESTINO_SIGLA") ) {
						de.addError("O campo dinamico 'ORGAO_DESTINO_SIGLA' so pode ser usado quando o tipo de destino for 'SETOR'");
					}
					
					if(campoDinamico.trim().equals("ORGAO_DESTINO_NOME") ) {
						de.addError("O campo dinamico 'ORGAO_DESTINO_NOME' so pode ser usado quando o tipo de destino for 'SETOR'");
					}
				}
				
				if(!tipoDestino.equals(TipoDestino.SETOR)) {
					if(campoDinamico.trim().equals("SETOR_DESTINO_SIGLA") ) {
						de.addError("O campo dinamico 'SETOR_DESTINO_SIGLA' so pode ser usado quando o tipo de destino for 'SETOR'");
					}
					
					if(campoDinamico.trim().equals("SETOR_DESTINO_DESCRICAO") ) {
						de.addError("O campo dinamico 'SETOR_DESTINO_DESCRICAO' so pode ser usado quando o tipo de destino for 'SETOR'");
					}
					if(campoDinamico.trim().equals("SETOR_DESTINO_ENDERECO") ) {
						de.addError("O campo dinamico 'SETOR_DESTINO_ENDERECO' so pode ser usado quando o tipo de destino for 'SETOR'");
					}
				}
				
			}
			
		}
		return this;
	}
	

	public ProtocoloValidator validarSeAEspecieExiste(EspecieBasicDto especie) {
		if(especie == null || especie.getId() == null) {
			de.addError("A especie selecionada não existe [id: " + dto.getEspecieId() + "]" );
		}
		return this;
	}

	public ProtocoloValidator validarSeOAssuntoExiste(AssuntoBasicDto assunto) {
		if(assunto == null || assunto.getId() == null) {
			de.addError("O assunto selecionado não existe [id: " + dto.getAssuntoId() + "]" );
		}
		return this;
	}

	public ProtocoloValidator validarSeOOrgaoInformadoComoInteressadoExiste(List<OrgaoPaeBasicDto> orgaos) {
		for(Long id : dto.getInteressadosOrgaosIds()) {
			if(!orgaos.contains(OrgaoPaeBasicDto.builder().id(id).build())) {
				de.addError("O órgão interessado selecionado não existe [id: " + id + "]" );
			}
		}
		return this;
	}
	
	public ProtocoloValidator validarSeOrgaoOrigemInformadoExiste(List<OrgaoPaeBasicDto> orgaos) {
		if(!orgaos.contains(OrgaoPaeBasicDto.builder().id(dto.getOrgaoOrigemId()).build())) {
			de.addError("O órgão origem selecionado não existe [id: " + dto.getOrgaoOrigemId() + "]" );
		}
		
		return this;
	}
	
	public ProtocoloValidator validarSeOsDestinosInformadosExistem(List<OrgaoPaeBasicDto> orgaos, List<LocalizacaoBasicDto> localizacoes) {
		if(dto.getTipoDestino().equals(TipoDestino.ORGAO)) {
			for(Long id : dto.getDestinosIds()) {
				if(!orgaos.contains(OrgaoPaeBasicDto.builder().id(id).build())) {
					de.addError("O órgão destino selecionado não existe [id: " + id + "]" );
				}
			}
		} else {
			for(Long id : dto.getDestinosIds()) {
				if(!localizacoes.contains(LocalizacaoBasicDto.builder().id(id).build())) {
					de.addError("A localizacao destino selecionado não existe [id: " + id + "]" );
				}
			}
		}
		return this;
	}
	
	public ProtocoloValidator validarSeAsLocalizacoesInteressadasExistem(List<LocalizacaoBasicDto> localizacoes) {
		for(Long id : dto.getInteressadosLocalizacoesIds()) {
			if(!localizacoes.contains(LocalizacaoBasicDto.builder().id(id).build())) {
				de.addError("A localizacao interessada selecionada não existe [id: " + id + "]" );
			}
		}
		return this;
	}
	
	public ProtocoloValidator validarSeALocalizacaoOrigemInformadaExiste(List<LocalizacaoBasicDto> localizacoes) {
		
		if(!localizacoes.contains(LocalizacaoBasicDto.builder().id(dto.getLocalizacaoOrigemId()).build())) {
			de.addError("A localizacao origem selecionada não existe [id: " + dto.getLocalizacaoOrigemId() + "]" );
		}
	
		return this;
	}

	public ProtocoloValidator validarSeTodosOsOrgaoPossuemSetorPadraoComResponsavel(List<OrgaoPaeBasicDto> orgaos) {
		if(orgaos != null && orgaos.size() > 0) {
			orgaos.stream().forEach( o -> {
				if(o.getLocalizacaoPadraoRecebimento() == null) {
					de.addError(String.format("O órgão s% não possui uma localização padrão", o.getSigla()));
				} else {
					if(o.getLocalizacaoPadraoRecebimento().getResponsavel() == null) {
						de.addError(String.format("O setor padrão do órgão s% não possui um responsável", o.getSigla()));
					}
				}
			});
		}
		return this;
	}
}
