package com.bertachiniprojetos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MyFileNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public MyFileNotFoundException(String message) {
		super(message);
	}
	
	public MyFileNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}

	
}
