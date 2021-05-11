package br.gov.pa.prodepa.pae.protocolo.domain.dto.suporte;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaeSuporteEnricherDto {

	private Set<Long> especiesId = new HashSet<Long>();
	private List<EspecieBasicDto> especies;
	
	private Set<Long> assuntosId = new HashSet<Long>();
	private List<AssuntoBasicDto> assuntos;
	
	private Set<Long> localizacoesId = new HashSet<Long>();
	private List<LocalizacaoComEnderecoDto> localizacoes;
	
	private Set<Long> orgaosId = new HashSet<Long>();
	private List<OrgaoPaeBasicDto> orgaos;

	public void addEspecieId(Long id) {
		especiesId.add(id);
	}
	
	public void addEspecieId(Collection<Long> id) {
		especiesId.addAll(id);
	}
	
	public void addAssuntoId(Long id) {
		assuntosId.add(id);
	}
	
	public void addAssuntoId(Collection<Long> id) {
		assuntosId.addAll(id);
	}
	
	public void addLocalizacaoId(Long id) {
		localizacoesId.add(id);
	}
	
	public void addLocalizacaoId(Collection<Long> id) {
		localizacoesId.addAll(id);
	}
	
	public void addOrgaoId(Long id) {
		orgaosId.add(id);
	}
	
	public void addOrgaoId(Collection<Long> id) {
		orgaosId.addAll(id);
	}

	public AssuntoBasicDto getAssunto(Long assuntoId) {
		return this.assuntos.stream()
				.filter( a -> a.getId().equals(assuntoId))
				.findFirst()
				.orElseThrow( () -> new RuntimeException("O assunto com o id " + assuntoId + " nao foi encontrado") );
	}

	public EspecieBasicDto getEspecie(Long especieId) {
		return this.especies.stream()
				.filter( a -> a.getId().equals(especieId))
				.findFirst()
				.orElseThrow( () -> new RuntimeException("A especie com o id " + especieId + " nao foi encontrada") );
	}
	
	public OrgaoPaeBasicDto getOrgao(Long id) {
		return this.orgaos.stream()
				.filter( a -> a.getId().equals(id))
				.findFirst()
				.orElseThrow( () -> new RuntimeException("O orgao com o id " + id + " nao foi encontrado") );
	}
	
	public LocalizacaoComEnderecoDto getLocalizacao(Long id) {
		return this.localizacoes.stream()
				.filter( a -> a.getId().equals(id))
				.findFirst()
				.orElseThrow( () -> new RuntimeException("A localizacao com o id " + id + " nao foi encontrada") );
	}

	public List<OrgaoPaeBasicDto> getOrgaos(List<Long> ids) {
		return this.orgaos.stream()
			.filter( a -> ids.contains(a.getId()))
			.collect(Collectors.toList());
	}

	public List<LocalizacaoComEnderecoDto> getLocalizacoes(List<Long> ids) {
		return this.localizacoes.stream()
				.filter( a -> ids.contains(a.getId()) )
				.collect(Collectors.toList());
	}

	public boolean contemAssuntoComId(Long assuntoId) {
		return assuntos != null && assuntos.stream().filter( a -> a.getId().equals(assuntoId)).count() > 0;
	}
	
	public boolean contemEspecieComId(Long id) {
		return especies != null && especies.stream().filter( a -> a.getId().equals(id)).count() > 0;
	}
	
	public boolean contemOrgaoComId(Long id) {
		return orgaos != null && orgaos.stream().filter( a -> a.getId().equals(id)).count() > 0;
	}
	
	public boolean contemLocalizacaoComId(Long id) {
		return localizacoes != null && localizacoes.stream().filter( a -> a.getId().equals(id)).count() > 0;
	}
	
}