package br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity.SequencialProtocoloEntity;

@Repository
public interface SequencialProtocoloJpaRepository extends JpaRepository<SequencialProtocoloEntity, Integer>{

	@Query(value = "select sequencial "
			+ "from pae.sequencial_protocolo "
			+ "where ano = :ano "
			+ "for update", 
			nativeQuery = true)
	Long buscarProximoSequencial(@Param("ano") Integer ano);

	@Modifying
	@Query(value = "insert into pae.sequencial_protocolo values(:ano,:sequenciaAtual)", nativeQuery = true)
	void insert(@Param("ano") Integer ano, @Param("sequenciaAtual") Long sequenciaAtual);

	@Modifying
	@Query(value = "update pae.sequencial_protocolo set sequencial = :sequencial "
			+ "where ano = :ano ",
			nativeQuery = true)
	void incrementarSequencial(@Param("ano") Integer ano, @Param("sequencial") Long sequencial);
}
