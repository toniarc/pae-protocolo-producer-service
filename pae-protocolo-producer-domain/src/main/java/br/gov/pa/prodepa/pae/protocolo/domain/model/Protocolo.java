package br.gov.pa.prodepa.pae.protocolo.domain.model;

import java.util.Date;
import java.util.List;

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
public class Protocolo {

	private ProtocoloId id;
	private Date dataProtocolo;
	private Long especieId;
	private Long assuntoId;
	private Long municipioId;
	private String origemDocumento;
	private String complemento;
	private String prioridade;
	private Long documentoProtocoladoId;
	private Long orgaoDestinoId;
	private Long localizacaoDestinoId;
	private Long numeroDocumento;
	private Integer anoDocumento;
	private Long usuarioCadastroId;
	private String usuarioCadastroNome;
	private Long orgaoOrigemId;
	private String orgaoOrigemSigla;
	private Long localizacaoOrigemId;
	private List<Interessado> interessados;
	private List<Anexo> anexos;
	
	private String hashAnexos;
	private String hashAlgoritmo;
	
}