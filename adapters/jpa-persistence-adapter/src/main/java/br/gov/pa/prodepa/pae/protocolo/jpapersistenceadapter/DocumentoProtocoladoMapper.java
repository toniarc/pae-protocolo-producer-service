package br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.gov.pa.prodepa.pae.protocolo.domain.model.DocumentoProtocolado;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity.DocumentoProtocoladoEntity;

@Mapper
public interface DocumentoProtocoladoMapper {

	DocumentoProtocoladoMapper INSTANCE = Mappers.getMapper( DocumentoProtocoladoMapper.class );
	
	DocumentoProtocolado map(DocumentoProtocoladoEntity entity);
	
	DocumentoProtocoladoEntity map(DocumentoProtocolado model);
}
