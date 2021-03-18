package br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import br.gov.pa.prodepa.pae.protocolo.domain.exception.SequencialDocumentoExistenteException;
import br.gov.pa.prodepa.pae.protocolo.domain.model.NumeroDocumentoReservado;
import br.gov.pa.prodepa.pae.protocolo.domain.port.SequencialDocumentoRepository;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity.NumeroDocumentoReservadoEntity;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.repository.NumeroDocumentoReservadoJpaRepository;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.repository.SequencialDocumentoJpaRepository;

@Component
public class SequenialDocumentoPersistenceAdapter implements SequencialDocumentoRepository{

	@Autowired
	private SequencialDocumentoJpaRepository repository;
	
	@Autowired
	private NumeroDocumentoReservadoJpaRepository reservaRepository;
	
	@Override
	public Long buscarProximoSequencial(Integer ano, Long especieId, Long localizacaoId) {
		Long sequencial = repository.buscarProximoSequencial(ano, especieId, localizacaoId);
		
		if (sequencial == null) {
			return null;
		}
		
		sequencial++;
		repository.incrementarSequencial(ano, especieId, localizacaoId, sequencial);
		return sequencial;
	}

	@Override
	public void criarSequencia(Integer ano, Long especieId, Long localizacaoId, Long sequenciaAtual) throws SequencialDocumentoExistenteException {
		try {
			repository.insert(ano, especieId, localizacaoId, sequenciaAtual);			
		} catch (DataIntegrityViolationException e) {
			throw new SequencialDocumentoExistenteException();
		}
	}

	@Override
	public NumeroDocumentoReservado salvar(NumeroDocumentoReservado reserva) {
		
		NumeroDocumentoReservadoEntity entity = NumeroDocumentoReservadoEntity.builder()
				.ano(reserva.getAno())
				.especieId(reserva.getEspecie().getId())
				.localizacaoUsuarioId(reserva.getLocalizacao().getId())
				.sequencial(reserva.getSequencial())
				.manutData(reserva.getManutData())
				.manutUsuarioId(reserva.getManutUsuarioId())
				.build();
		NumeroDocumentoReservadoEntity savedEntity = reservaRepository.save(entity);
		reserva.setId(savedEntity.getId());
		
		return reserva;
	}

	@Override
	public NumeroDocumentoReservado buscarNumeroReservado(Long numeroReservadoId) {
		return reservaRepository.findReservaById(numeroReservadoId);
	}
	
}
