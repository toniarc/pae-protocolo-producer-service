package br.gov.pa.prodepa.pae.protocolo.domain.port;

public interface ObjectStorageService {

	void putObject(String uniqueId, byte[] file, String contentType);

}
