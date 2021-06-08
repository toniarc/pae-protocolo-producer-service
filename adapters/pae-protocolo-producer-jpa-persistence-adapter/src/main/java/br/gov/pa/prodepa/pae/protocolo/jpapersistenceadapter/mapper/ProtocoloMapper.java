package br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.gov.pa.prodepa.pae.protocolo.domain.model.Protocolo;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity.ProtocoloEntity;

@Mapper
public interface ProtocoloMapper {

	ProtocoloMapper INSTANCE = Mappers.getMapper( ProtocoloMapper.class );
	
	ProtocoloEntity map(Protocolo model);
	
	Protocolo map(ProtocoloEntity entity);
}
