package br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import br.gov.pa.prodepa.pae.protocolo.domain.dto.ProtocoloResponseDto;
import br.gov.pa.prodepa.pae.protocolo.domain.model.Protocolo;
import br.gov.pa.prodepa.pae.protocolo.domain.port.ProtocoloRepository;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity.AnexoEntity;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity.AssinaturaEntity;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity.InteressadoEntity;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity.ProtocoloEntity;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.mapper.ProtocoloMapper;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.repository.ProtocoloJpaRepository;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.repository.SequencialProtocoloJpaRepository;

@Component
public class ProtocoloPersistenceAdapter implements ProtocoloRepository {

	@Autowired
	private ProtocoloJpaRepository repository;
	
	@Autowired
	private SequencialProtocoloJpaRepository sequencialProtocoloRepository;
	
	@Override
	public ProtocoloResponseDto salvar(Protocolo protocolo) {
		
		ProtocoloEntity entity = ProtocoloMapper.INSTANCE.map(protocolo);

		repository.getOne(entity.getId());
		
		if(entity.getInteressados() != null) {
			for(InteressadoEntity i : entity.getInteressados()) {
				i.setProtocolo(entity);
			}
		}
		
		if(entity.getAnexos() != null) {
			for(AnexoEntity an : entity.getAnexos()) {
				an.setProtocolo(entity);
				
				if(an.getAssinaturas() != null) {
					for(AssinaturaEntity a : an.getAssinaturas()) {
						a.setAnexo(an);
					}
				}
			}
		}
		
		repository.saveAndFlush(entity);
		return ProtocoloResponseDto.builder().ano(protocolo.getId().getAnoProtocolo()).numero(protocolo.getId().getNumeroProtocolo()).build();
	}

	@Override
	public Long buscarProximoSequencial(Integer ano) {
		
		StopWatch watch = new StopWatch();
		watch.start();
		
		Long sequencial = sequencialProtocoloRepository.buscarProximoSequencial(ano);
		
		watch.stop();
		System.out.println("Consulta Sequencial: " + watch.getTotalTimeMillis()  + " milisegundos");
		
		if (sequencial == null) {
			return null;
		}
		
		watch = new StopWatch();
		watch.start();
		
		sequencial++;
		sequencialProtocoloRepository.incrementarSequencial(ano, sequencial);
		
		watch.stop();
		System.out.println("Incremento Sequencial: " + watch.getTotalTimeMillis()  + " milisegundos");
		
		return sequencial;
	}

	@Override
	public void criarSequencia(int ano, Long sequencial) {
		sequencialProtocoloRepository.insert(ano, sequencial);
	}

	@Override
	public boolean jaExisteSequenciaDeProtocoloIniciada() {
		return sequencialProtocoloRepository.count() > 0;
	}

}
