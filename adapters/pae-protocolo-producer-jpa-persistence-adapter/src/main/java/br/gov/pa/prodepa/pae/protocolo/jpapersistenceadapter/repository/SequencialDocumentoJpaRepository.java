package br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity.SequencialDocumentoEntity;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity.SequencialDocumentoEntityId;

@Repository
public interface SequencialDocumentoJpaRepository extends JpaRepository<SequencialDocumentoEntity, SequencialDocumentoEntityId> {

	@Query(value = "select sequencial "
			+ "from pae.sequencial_documento "
			+ "where ano = :ano "
			+ "and   especie_id = :especieId "
			+ "and	 localizacao_id = :localizacaoId "
			+ "for update", 
			nativeQuery = true)
	Long buscarProximoSequencial(@Param("ano") Integer ano, @Param("especieId") Long especieId, @Param("localizacaoId") Long localizacaoId);

	@Modifying
	@Query(value = "insert into pae.sequencial_documento values(:ano,:sequenciaAtual,:especieId,:localizacaoId)", nativeQuery = true)
	void insert(@Param("ano") Integer ano, @Param("especieId") Long especieId, @Param("localizacaoId") Long localizacaoId, @Param("sequenciaAtual") Long sequenciaAtual);

	@Modifying
	@Query(value = "update pae.sequencial_documento set sequencial = :sequencial "
			+ "where ano = :ano "
			+ "and   especie_id = :especieId "
			+ "and   localizacao_id = :localizacaoId ",
			nativeQuery = true)
	void incrementarSequencial(@Param("ano") Integer ano, @Param("especieId") Long especieId, @Param("localizacaoId") Long localizacaoId, @Param("sequencial") Long sequencial);

}
