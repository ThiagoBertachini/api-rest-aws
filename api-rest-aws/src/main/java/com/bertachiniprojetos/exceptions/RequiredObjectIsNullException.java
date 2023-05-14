package com.bertachiniprojetos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public RequiredObjectIsNullException() {
		super("Persisting object must not be null");
	}
	
	public RequiredObjectIsNullException(String message) {
		super(message);
	}
	
}
