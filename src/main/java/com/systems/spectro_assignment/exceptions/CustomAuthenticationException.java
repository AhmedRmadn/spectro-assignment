package com.systems.spectro_assignment.exceptions;

import org.springframework.http.HttpStatus;

public class CustomAuthenticationException extends RuntimeException{
	private HttpStatus httpStatus;
	public CustomAuthenticationException(String message , HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	

}
