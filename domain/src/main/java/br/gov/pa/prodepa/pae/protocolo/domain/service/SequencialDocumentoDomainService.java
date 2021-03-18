package br.gov.pa.prodepa.pae.protocolo.domain.service;

import java.time.LocalDate;
import java.util.Date;

import br.gov.pa.prodepa.pae.protocolo.domain.exception.SequencialDocumentoExistenteException;
import br.gov.pa.prodepa.pae.protocolo.domain.model.NumeroDocumentoReservado;
import br.gov.pa.prodepa.pae.protocolo.domain.model.SequencialDocumento;
import br.gov.pa.prodepa.pae.protocolo.domain.model.Usuario;
import br.gov.pa.prodepa.pae.protocolo.domain.port.SequencialDocumentoRepository;
import br.gov.pa.prodepa.pae.protocolo.domain.port.TransactionalService;
import br.gov.pa.prodepa.pae.suporte.client.EspecieBasicDto;
import br.gov.pa.prodepa.pae.suporte.client.LocalizacaoBasicDto;

public class SequencialDocumentoDomainService implements SequencialDocumentoService {

	private final SequencialDocumentoRepository repository;
	
	private final Usuario usuarioLogado;

	private final TransactionalService transactionalService;
	
	public SequencialDocumentoDomainService(SequencialDocumentoRepository repository, Usuario usuarioLogado, 
			TransactionalService transactionalService) {
		this.repository = repository;
		this.usuarioLogado = usuarioLogado;
		this.transactionalService = transactionalService;
	}
	
	public SequencialDocumento criarNovaSequencia(Long especieId, Long localizacaoId) throws SequencialDocumentoExistenteException {
		
		EspecieBasicDto especie = new EspecieBasicDto(especieId, "");
		LocalizacaoBasicDto localizacao = new LocalizacaoBasicDto(localizacaoId, "");
		
		int ano = LocalDate.now().getYear();
		Long sequencial = 1L;
		repository.criarSequencia(ano, especieId, localizacaoId, sequencial);
		return new SequencialDocumento(ano, especie, localizacao, sequencial);
	}
	
	public NumeroDocumentoReservado reservarNumeroDocumento(Long especieId, Long localizacaoId) {
		return transactionalService.executarEmTransacaoSeparada(status -> {
			
			SequencialDocumento sequencialDocumento = buscarProximoSequencial(especieId, localizacaoId);
			
			NumeroDocumentoReservado reserva = NumeroDocumentoReservado.builder()
					.ano(sequencialDocumento.getAno())
					.localizacao(sequencialDocumento.getLocalizacao())
					.especie(sequencialDocumento.getEspecie())
					.sequencial(sequencialDocumento.getSequencial())
					.manutData(new Date())
					.manutUsuarioId(usuarioLogado.getId())
					.build();
			
			return repository.salvar(reserva);
		});
	}
	
	/**
	 * Cada chamada de transactionTemplate.execute cria uma transacao nova
	 * 
	 * @param especieId
	 * @param localizacaoId
	 * @return
	 */
	public SequencialDocumento buscarProximoSequencial(Long especieId, Long localizacaoId) {
		
		EspecieBasicDto especie = new EspecieBasicDto(especieId, ""); //TODO verificar se existe
		LocalizacaoBasicDto localizacao = new LocalizacaoBasicDto(localizacaoId, ""); //TODO verificar se existe
		
		int ano = LocalDate.now().getYear();
		
		SequencialDocumento sequenciaDocumento = transactionalService.executarEmTransacaoSeparada(status -> {
			if(!status.isNewTransaction()) {
				throw new RuntimeException("O metodo 'buscarProximoSequencial' deve executar em uma transacao separada.");
			}
			
			/**
			 * executa uma consulta usando 'SELEC .. FOR UPDATE' definir um lock no registro em questao
			 * e, se retornar alguma tupla, incrementa o sequencial e em faz um update da sequencia incrementada.
			 * Por fim, retorna o valor atual da sequencia
			 */
			Long sequencial = repository.buscarProximoSequencial(ano, especieId, localizacaoId);
			return new SequencialDocumento(ano, especie, localizacao, sequencial);
		});
		
		if(sequenciaDocumento == null) {
			try {
				return transactionalService.executarEmTransacaoSeparada(status -> criarNovaSequencia(especieId, localizacaoId));
			} catch (SequencialDocumentoExistenteException e) {
				/**
				 * caso ocorra de nesse meio tempo outra thread cadastrar a mesma sequence, entao a aplicacao 
				 * apenas busca novamente usando os mesmo parametros
				 */
				return transactionalService.executarEmTransacaoSeparada(status -> {
					Long sequencial = repository.buscarProximoSequencial(ano, especieId, localizacaoId);
					return new SequencialDocumento(ano, especie, localizacao, sequencial);
				});
			}
		}
		
		return sequenciaDocumento;
	}
}
