package br.gov.pa.prodepa.pae.protocolo.nutanix.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.gov.pa.prodepa.pae.protocolo.domain.port.ObjectStorageService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;

@Component
public class NutanixClientService implements ObjectStorageService {

	private final String acessKeyId;
	
	private final String secretAcessKey;
	
	private final String endpointUrl;

	private static final String BUCKET = "pae4";
	
	private final MinioClient minioClient;
	
	public NutanixClientService(
			@Value("${nutanix.endpoint-url}") String endpointUrl,
			@Value("${nutanix.access-key}") String acessKeyId, 
			@Value("${nutanix.secret-key}") String secretAcessKey) {
		
		this.endpointUrl = endpointUrl;
		this.acessKeyId = acessKeyId;
		this.secretAcessKey = secretAcessKey;
		
		try {
			minioClient = criarClienteMinio();
			boolean found = verificarSeTodosOsBucketsJaForamCriados();
			
			if(!found) {
				minioClient.makeBucket(MakeBucketArgs.builder().bucket("pae04").build()); 
			}
			
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IllegalArgumentException | IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private MinioClient criarClienteMinio() {
		return MinioClient.builder()
	              .endpoint(endpointUrl)
	              .credentials(acessKeyId, secretAcessKey)
	              .build();
	}
	
	private boolean verificarSeTodosOsBucketsJaForamCriados() {
		try {
			return minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET).build());
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IllegalArgumentException | IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void putObject(String uniqueId, byte[] file, String contentType) {

			ByteArrayInputStream bais = new ByteArrayInputStream(file);

			try {
				minioClient.putObject(
						PutObjectArgs.builder()
							.bucket(BUCKET)
							.object(uniqueId)
							.contentType(contentType)
							.stream(bais, bais.available(), -1)
							.build());
			} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
					| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
					| IllegalArgumentException | IOException e) {
				throw new RuntimeException("Ocorreu um erro durante o upload do arquivo", e);
			} finally {
				try {
					if(bais != null)
						bais.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

	}
	
}