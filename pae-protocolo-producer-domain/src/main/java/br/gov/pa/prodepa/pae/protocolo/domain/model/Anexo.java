package br.gov.pa.prodepa.pae.protocolo.domain.model;

import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Anexo {

	private Integer id;
	private Protocolo protocolo;
	private Boolean assinadoPorTodos;
	private Integer quantidadeAssinaturas;
	private String conteudo;
	private Date anexadoEm;
	private String s3StorageId;
	private Float tamanhoArquivoMb;
	private Integer quantidadePaginas;
	private Long assinanteId;
	private Boolean documentoInicial;
	private Long sequencial;
	private String hashArquivo;
	private String hashAlgoritmo;
	private Set<Assinatura> assinaturas;
}
