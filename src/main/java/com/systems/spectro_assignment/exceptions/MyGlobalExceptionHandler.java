package com.systems.spectro_assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.systems.spectro_assignment.responses.ApiResponse;

import jakarta.validation.ConstraintViolationException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyGlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		Map<String, String> response = new HashMap<>();
		e.getBindingResult().getAllErrors().forEach(err -> {
			String fieldName = ((FieldError) err).getField();
			String message = err.getDefaultMessage();
			response.put(fieldName, message);
		});
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponse<Void>> handleConstraintViolationException(ConstraintViolationException e) {
		ApiResponse<Void> response = new ApiResponse<Void>();
		response.setSuccess(false);
		response.setMessage(e.getMessage());
		return new ResponseEntity<ApiResponse<Void>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomAuthenticationException.class)
	public ResponseEntity<ApiResponse<Void>> HadleAuthException(CustomAuthenticationException e){
		ApiResponse<Void> response = new ApiResponse<Void>();
		response.setSuccess(false);
		response.setMessage(e.getMessage());
		return new ResponseEntity<ApiResponse<Void>>(response, e.getHttpStatus());
	}
	
	@ExceptionHandler(StudentException.class)
	public ResponseEntity<ApiResponse<Void>> handleStudentException(StudentException e){
		ApiResponse<Void> response = new ApiResponse<Void>();
		response.setSuccess(false);
		response.setMessage(e.getMessage());
		return new ResponseEntity<ApiResponse<Void>>(response, e.getHttpStatus());
	}


}
