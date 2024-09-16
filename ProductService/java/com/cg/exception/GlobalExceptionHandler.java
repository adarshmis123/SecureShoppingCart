package com.cg.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ExceptionHandle.class)
	public final ResponseEntity<Object> handleAllExceptions(ExceptionHandle ex, WebRequest request ){
		ExceptionResponse exceptionResponse = new ExceptionResponse("500 Internal Server Error",ex.getMessage(),
				request.getDescription(false),LocalDate.now());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
}
