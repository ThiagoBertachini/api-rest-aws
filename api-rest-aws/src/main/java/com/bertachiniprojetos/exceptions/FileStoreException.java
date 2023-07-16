package com.bertachiniprojetos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileStoreException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public FileStoreException(String message) {
		super(message);
	}
	
	public FileStoreException(String message, Throwable throwable) {
		super(message, throwable);
	}

	
}
