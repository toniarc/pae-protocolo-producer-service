package br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gov.pa.prodepa.pae.protocolo.domain.model.NumeroDocumentoReservado;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity.NumeroDocumentoReservadoEntity;

@Repository
public interface NumeroDocumentoReservadoJpaRepository extends JpaRepository<NumeroDocumentoReservadoEntity, Long>{

	@Query(value=" select * "
			+ "from pae.numero_documento_reservado nr "
			+ "where nr.id = :numeroReservadoId "
			+ "for update ", 
			nativeQuery=true)
	NumeroDocumentoReservadoEntity findReservaById(@Param("numeroReservadoId") Long numeroReservadoId);

	@Query(value=" select new br.gov.pa.prodepa.pae.protocolo.domain.model.NumeroDocumentoReservado(nr.id, nr.ano, "
			+ " nr.especieId, nr.localizacaoId, nr.sequencial) "
			+ " from NumeroDocumentoReservadoEntity nr "
			+ " where nr.especieId = :especieId "
			+ " and nr.localizacaoId = :localizacaoId "
			+ " and nr.documentoProtocolado is null ")
    List<NumeroDocumentoReservado> listarNumerosReservados(@Param("especieId") Long especieId, @Param("localizacaoId") Long localizacaoId);

}
