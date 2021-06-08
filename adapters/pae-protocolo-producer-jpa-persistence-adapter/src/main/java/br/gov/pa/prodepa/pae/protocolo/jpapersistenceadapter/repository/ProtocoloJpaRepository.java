package br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity.ProtocoloEntity;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity.ProtocoloEntityId;

@Repository
public interface ProtocoloJpaRepository extends JpaRepository<ProtocoloEntity, ProtocoloEntityId> {

}
