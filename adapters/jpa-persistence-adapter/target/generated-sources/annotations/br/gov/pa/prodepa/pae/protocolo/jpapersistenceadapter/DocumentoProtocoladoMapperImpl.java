package br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter;

import br.gov.pa.prodepa.pae.protocolo.domain.model.DocumentoProtocolado;
import br.gov.pa.prodepa.pae.protocolo.domain.model.DocumentoProtocolado.DocumentoProtocoladoBuilder;
import br.gov.pa.prodepa.pae.protocolo.jpapersistenceadapter.entity.DocumentoProtocoladoEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-22T03:48:39-0300",
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
        List<Long> list1 = entity.getDestinosIds();
        if ( list1 != null ) {
            documentoProtocolado.destinosIds( new ArrayList<Long>( list1 ) );
        }
        documentoProtocolado.documentoId( entity.getDocumentoId() );
        documentoProtocolado.especieId( entity.getEspecieId() );
        documentoProtocolado.id( entity.getId() );
        documentoProtocolado.localizacaoOrigemId( entity.getLocalizacaoOrigemId() );
        List<Long> list2 = entity.getLocalizacoesInteressadasIds();
        if ( list2 != null ) {
            documentoProtocolado.localizacoesInteressadasIds( new ArrayList<Long>( list2 ) );
        }
        if ( entity.getModeloConteudoId() != null ) {
            documentoProtocolado.modeloConteudoId( Long.parseLong( entity.getModeloConteudoId() ) );
        }
        documentoProtocolado.municipioId( entity.getMunicipioId() );
        documentoProtocolado.orgaoOrigemId( entity.getOrgaoOrigemId() );
        List<Long> list3 = entity.getOrgaosInteressadosIds();
        if ( list3 != null ) {
            documentoProtocolado.orgaosInteressadosIds( new ArrayList<Long>( list3 ) );
        }
        documentoProtocolado.origemDocumento( entity.getOrigemDocumento() );
        List<Long> list4 = entity.getPessoasFisicasInteressadasIds();
        if ( list4 != null ) {
            documentoProtocolado.pessoasFisicasInteressadasIds( new ArrayList<Long>( list4 ) );
        }
        List<Long> list5 = entity.getPessoasJuridicasInteressadasIds();
        if ( list5 != null ) {
            documentoProtocolado.pessoasJuridicasInteressadasIds( new ArrayList<Long>( list5 ) );
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
        List<Long> list1 = model.getDestinosIds();
        if ( list1 != null ) {
            documentoProtocoladoEntity.setDestinosIds( new ArrayList<Long>( list1 ) );
        }
        documentoProtocoladoEntity.setDocumentoId( model.getDocumentoId() );
        documentoProtocoladoEntity.setEspecieId( model.getEspecieId() );
        documentoProtocoladoEntity.setId( model.getId() );
        documentoProtocoladoEntity.setLocalizacaoOrigemId( model.getLocalizacaoOrigemId() );
        List<Long> list2 = model.getLocalizacoesInteressadasIds();
        if ( list2 != null ) {
            documentoProtocoladoEntity.setLocalizacoesInteressadasIds( new ArrayList<Long>( list2 ) );
        }
        if ( model.getModeloConteudoId() != null ) {
            documentoProtocoladoEntity.setModeloConteudoId( String.valueOf( model.getModeloConteudoId() ) );
        }
        documentoProtocoladoEntity.setMunicipioId( model.getMunicipioId() );
        documentoProtocoladoEntity.setOrgaoOrigemId( model.getOrgaoOrigemId() );
        List<Long> list3 = model.getOrgaosInteressadosIds();
        if ( list3 != null ) {
            documentoProtocoladoEntity.setOrgaosInteressadosIds( new ArrayList<Long>( list3 ) );
        }
        documentoProtocoladoEntity.setOrigemDocumento( model.getOrigemDocumento() );
        List<Long> list4 = model.getPessoasFisicasInteressadasIds();
        if ( list4 != null ) {
            documentoProtocoladoEntity.setPessoasFisicasInteressadasIds( new ArrayList<Long>( list4 ) );
        }
        List<Long> list5 = model.getPessoasJuridicasInteressadasIds();
        if ( list5 != null ) {
            documentoProtocoladoEntity.setPessoasJuridicasInteressadasIds( new ArrayList<Long>( list5 ) );
        }
        documentoProtocoladoEntity.setPrioridade( model.getPrioridade() );
        documentoProtocoladoEntity.setSequencial( model.getSequencial() );
        documentoProtocoladoEntity.setTipoDestino( model.getTipoDestino() );

        return documentoProtocoladoEntity;
    }
}
