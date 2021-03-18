package br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pa.prodepa.pae.protocolo.domain.model.DocumentoProtocolado;
import br.gov.pa.prodepa.pae.protocolo.domain.port.DocumentoProtocoladoRepository;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity.DocumentoProtocoladoEntity;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.repository.DocumentoProtocoladoJpaRepository;

@Component
public class DocumentoProtocoladoPersistenceAdapter implements DocumentoProtocoladoRepository {

	private DocumentoProtocoladoJpaRepository repository;
	
	@Autowired
	public DocumentoProtocoladoPersistenceAdapter(DocumentoProtocoladoJpaRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public DocumentoProtocolado salvar(DocumentoProtocolado dp) {
		DocumentoProtocoladoEntity entity = DocumentoProtocoladoMapper.INSTANCE.map(dp);
		DocumentoProtocoladoEntity entitySaved = repository.saveAndFlush(entity);
		return DocumentoProtocoladoMapper.INSTANCE.map(entitySaved);
	}

}
