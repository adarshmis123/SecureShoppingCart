package com.cg.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(UserProfileException.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		System.out.println("exception ...");
		ex.printStackTrace();
	    ExceptionResponse exceptionResponse =new ExceptionResponse(ex.getMessage(), request.getDescription(false),
	    		"500 Internal Server Error", LocalDateTime.now());
	    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<ExceptionResponse> handleExceptions(Exception ex, WebRequest request) {
	        logger.error("Exception occurred: ", ex);
	        ExceptionResponse errorResponse = new ExceptionResponse(ex.getMessage(), request.getDescription(false),
	                "500 Internal Server Error", LocalDateTime.now());
	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		BindingResult bindingResult = ex.getBindingResult();
	    Stream<FieldError> stream = bindingResult.getFieldErrors().stream();
	    Stream<String> map = stream.map(error -> error.getField() + ": " +error.getDefaultMessage());
	    List<String> list = map.toList();
	            
	    ExceptionResponse exceptionResponse = new ExceptionResponse( "Validation Failed", 
	        list.toString(), "400 Bad Request",LocalDateTime.now());
	    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
		
	}
}
