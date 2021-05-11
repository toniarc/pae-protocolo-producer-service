package br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gov.pa.prodepa.pae.protocolo.domain.model.NumeroDocumentoReservado;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity.NumeroDocumentoReservadoEntity;

@Repository
public interface NumeroDocumentoReservadoJpaRepository extends JpaRepository<NumeroDocumentoReservadoEntity, Long>{

	@Query(value="select new br.gov.pa.prodepa.pae.protocolo.domain.model.NumeroDocumentoReservado(nr.id, nr.ano, "
			+ "nr.especie_id, nr.localizacao_usuario_id, nr.sequencial, nr.documento_protocolado_id) "
			+ "from pae.numero_documento_reservado nr "
			+ "where nr.id = :numeroReservadoId "
			+ "for update ", 
			nativeQuery=true)
	NumeroDocumentoReservado findReservaById(@Param("numeroReservadoId")Long numeroReservadoId);

}
