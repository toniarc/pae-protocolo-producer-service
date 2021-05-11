package br.gov.pa.prodepa.pae.protocolo.domain.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaPaginadaDto<T> {

	private int totalPages;
	private int totalElements;
	private int currentPage;
	private List<T> content;
	
	public ConsultaPaginadaDto() {
	}
	
	public ConsultaPaginadaDto(int totalPages, int totalElements, int currentPage, List<T> content) {
		super();
		this.totalPages = totalPages;
		this.totalElements = totalElements;
		this.content = content;
		this.currentPage = currentPage;
	}
	
	public List<T> getContent(){
		if(this.content == null) {
			return new ArrayList<T>(); 
		}
		return this.content;
	}
}
