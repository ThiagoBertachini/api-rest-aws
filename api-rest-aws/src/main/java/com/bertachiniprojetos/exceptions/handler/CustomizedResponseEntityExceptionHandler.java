package com.bertachiniprojetos.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bertachiniprojetos.exceptions.ExceptionResponse;
import com.bertachiniprojetos.exceptions.InvalidJwtAuthenticationException;
import com.bertachiniprojetos.exceptions.RequiredObjectIsNullException;
import com.bertachiniprojetos.exceptions.ResourceNotFoundException;

@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllException(Exception ex,
			WebRequest webRequest){
		
		ExceptionResponse exResponse = new ExceptionResponse(
				new Date(), 
				ex.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<>(exResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleNotFoundException(Exception ex,
			WebRequest webRequest){
		
		ExceptionResponse exResponse = new ExceptionResponse(
				new Date(), 
				ex.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<>(exResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RequiredObjectIsNullException.class)
	public final ResponseEntity<ExceptionResponse> handleBadRequestException(Exception ex,
			WebRequest webRequest){
		
		ExceptionResponse exResponse = new ExceptionResponse(
				new Date(), 
				ex.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<>(exResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidJwtAuthenticationException.class)
	public final ResponseEntity<ExceptionResponse> handleInvalidJwtAuthenticationException(Exception ex,
			WebRequest webRequest){
		
		ExceptionResponse exResponse = new ExceptionResponse(
				new Date(), 
				ex.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<>(exResponse, HttpStatus.FORBIDDEN);
	}
}
