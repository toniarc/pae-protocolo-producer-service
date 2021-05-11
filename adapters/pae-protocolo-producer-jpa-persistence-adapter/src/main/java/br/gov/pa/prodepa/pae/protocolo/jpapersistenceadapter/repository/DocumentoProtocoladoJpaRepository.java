package br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity.DocumentoProtocoladoEntity;

@Repository
public interface DocumentoProtocoladoJpaRepository extends JpaRepository<DocumentoProtocoladoEntity, Long> {

}
