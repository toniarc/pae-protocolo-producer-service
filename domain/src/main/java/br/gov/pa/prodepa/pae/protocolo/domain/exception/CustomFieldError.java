package br.gov.pa.prodepa.pae.protocolo.domain.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomFieldError {

	private String field;
	private String message;
	
	public CustomFieldError(String field, String message) {
		super();
		this.field = field;
		this.message = message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomFieldError other = (CustomFieldError) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomFieldError [ field=" + field + ", message=" + message + "]";
	}
	
}

