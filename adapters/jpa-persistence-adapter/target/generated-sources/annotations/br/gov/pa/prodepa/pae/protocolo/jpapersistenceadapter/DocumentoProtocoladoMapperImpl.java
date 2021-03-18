package br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter;

import br.gov.pa.prodepa.pae.protocolo.domain.model.DocumentoProtocolado;
import br.gov.pa.prodepa.pae.protocolo.domain.model.DocumentoProtocolado.DocumentoProtocoladoBuilder;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity.DocumentoProtocoladoEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-18T08:21:28-0300",
    comments = "version: 1.4.1.Final, compiler: Eclipse JDT (IDE) 3.24.0.v20201123-0742, environment: Java 15.0.1 (Oracle Corporation)"
)
public class DocumentoProtocoladoMapperImpl implements DocumentoProtocoladoMapper {

    @Override
    public DocumentoProtocolado map(DocumentoProtocoladoEntity entity) {
        if ( entity == null ) {
            return null;
        }

        DocumentoProtocoladoBuilder documentoProtocolado = DocumentoProtocolado.builder();

        if ( entity.getAno() != null ) {
            documentoProtocolado.ano( entity.getAno().intValue() );
        }
        List<Long> list = entity.getAssinantesIds();
        if ( list != null ) {
            documentoProtocolado.assinantesIds( new ArrayList<Long>( list ) );
        }
        documentoProtocolado.assuntoId( entity.getAssuntoId() );
        documentoProtocolado.complemento( entity.getComplemento() );
        documentoProtocolado.conteudoDocumento( entity.getConteudoDocumento() );
        documentoProtocolado.dataCadastro( entity.getDataCadastro() );
        List<Long> list1 = entity.getDestinoIds();
        if ( list1 != null ) {
            documentoProtocolado.destinoIds( new ArrayList<Long>( list1 ) );
        }
        documentoProtocolado.documentoId( entity.getDocumentoId() );
        documentoProtocolado.especieId( entity.getEspecieId() );
        documentoProtocolado.id( entity.getId() );
        List<Long> list2 = entity.getLocalizacaoInteressadoIds();
        if ( list2 != null ) {
            documentoProtocolado.localizacaoInteressadoIds( new ArrayList<Long>( list2 ) );
        }
        documentoProtocolado.localizacaoOrigemId( entity.getLocalizacaoOrigemId() );
        if ( entity.getModeloConteudoId() != null ) {
            documentoProtocolado.modeloConteudoId( Long.parseLong( entity.getModeloConteudoId() ) );
        }
        documentoProtocolado.municipioId( entity.getMunicipioId() );
        List<Long> list3 = entity.getOrgaoInteressadoIds();
        if ( list3 != null ) {
            documentoProtocolado.orgaoInteressadoIds( new ArrayList<Long>( list3 ) );
        }
        documentoProtocolado.orgaoOrigemId( entity.getOrgaoOrigemId() );
        documentoProtocolado.origemDocumento( entity.getOrigemDocumento() );
        List<Long> list4 = entity.getPessoaFisicaInteressadoIds();
        if ( list4 != null ) {
            documentoProtocolado.pessoaFisicaInteressadoIds( new ArrayList<Long>( list4 ) );
        }
        List<Long> list5 = entity.getPessoaJuridicaInteressadoIds();
        if ( list5 != null ) {
            documentoProtocolado.pessoaJuridicaInteressadoIds( new ArrayList<Long>( list5 ) );
        }
        documentoProtocolado.prioridade( entity.getPrioridade() );
        documentoProtocolado.sequencial( entity.getSequencial() );
        documentoProtocolado.tipoDestino( entity.getTipoDestino() );

        return documentoProtocolado.build();
    }

    @Override
    public DocumentoProtocoladoEntity map(DocumentoProtocolado model) {
        if ( model == null ) {
            return null;
        }

        DocumentoProtocoladoEntity documentoProtocoladoEntity = new DocumentoProtocoladoEntity();

        if ( model.getAno() != null ) {
            documentoProtocoladoEntity.setAno( model.getAno().shortValue() );
        }
        List<Long> list = model.getAssinantesIds();
        if ( list != null ) {
            documentoProtocoladoEntity.setAssinantesIds( new ArrayList<Long>( list ) );
        }
        documentoProtocoladoEntity.setAssuntoId( model.getAssuntoId() );
        documentoProtocoladoEntity.setComplemento( model.getComplemento() );
        documentoProtocoladoEntity.setConteudoDocumento( model.getConteudoDocumento() );
        documentoProtocoladoEntity.setDataCadastro( model.getDataCadastro() );
        List<Long> list1 = model.getDestinoIds();
        if ( list1 != null ) {
            documentoProtocoladoEntity.setDestinoIds( new ArrayList<Long>( list1 ) );
        }
        documentoProtocoladoEntity.setDocumentoId( model.getDocumentoId() );
        documentoProtocoladoEntity.setEspecieId( model.getEspecieId() );
        documentoProtocoladoEntity.setId( model.getId() );
        List<Long> list2 = model.getLocalizacaoInteressadoIds();
        if ( list2 != null ) {
            documentoProtocoladoEntity.setLocalizacaoInteressadoIds( new ArrayList<Long>( list2 ) );
        }
        documentoProtocoladoEntity.setLocalizacaoOrigemId( model.getLocalizacaoOrigemId() );
        if ( model.getModeloConteudoId() != null ) {
            documentoProtocoladoEntity.setModeloConteudoId( String.valueOf( model.getModeloConteudoId() ) );
        }
        documentoProtocoladoEntity.setMunicipioId( model.getMunicipioId() );
        List<Long> list3 = model.getOrgaoInteressadoIds();
        if ( list3 != null ) {
            documentoProtocoladoEntity.setOrgaoInteressadoIds( new ArrayList<Long>( list3 ) );
        }
        documentoProtocoladoEntity.setOrgaoOrigemId( model.getOrgaoOrigemId() );
        documentoProtocoladoEntity.setOrigemDocumento( model.getOrigemDocumento() );
        List<Long> list4 = model.getPessoaFisicaInteressadoIds();
        if ( list4 != null ) {
            documentoProtocoladoEntity.setPessoaFisicaInteressadoIds( new ArrayList<Long>( list4 ) );
        }
        List<Long> list5 = model.getPessoaJuridicaInteressadoIds();
        if ( list5 != null ) {
            documentoProtocoladoEntity.setPessoaJuridicaInteressadoIds( new ArrayList<Long>( list5 ) );
        }
        documentoProtocoladoEntity.setPrioridade( model.getPrioridade() );
        documentoProtocoladoEntity.setSequencial( model.getSequencial() );
        documentoProtocoladoEntity.setTipoDestino( model.getTipoDestino() );

        return documentoProtocoladoEntity;
    }
}
