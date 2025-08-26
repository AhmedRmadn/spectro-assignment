package com.systems.spectro_assignment.exceptions;

import org.springframework.http.HttpStatus;

public class StudentException extends RuntimeException{
	
	private HttpStatus httpStatus;
	public StudentException(String message , HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
