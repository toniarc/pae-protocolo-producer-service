package br.gov.pa.prodepa.pae.protocolo.domain.exception;

public class EntityNotFoundException extends DomainException {

	private static final long serialVersionUID = -5365443718593433185L;
	
	public EntityNotFoundException(String message) {
		super(message, 404);
	}

}
