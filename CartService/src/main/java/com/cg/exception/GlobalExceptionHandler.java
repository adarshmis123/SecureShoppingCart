package com.cg.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		List<String> errorList = new ArrayList<>();
		for (FieldError error : fieldErrors) {
			errorList.add(error.getField() + ": " + error.getDefaultMessage());
		}

		String details = String.join(", ", errorList);
		ExceptionResponse errorResponse = new ExceptionResponse("Validation failed.", details, "400 Bad Request",
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<ExceptionResponse> Exceptions(FeignException ex, WebRequest request) {
		logger.error("Exception occurred: ", ex);
		ExceptionResponse errorResponse = new ExceptionResponse(ex.getMessage(), request.getDescription(false),
				"500 Internal Server Error", LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(CartServiceException.class)
	public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
		logger.error("Exception occurred: ", ex);
		ExceptionResponse errorResponse = new ExceptionResponse(ex.getMessage(), request.getDescription(false),
				"500 Internal Server Error", LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> Exceptions(Exception ex, WebRequest request) {
		logger.error("Exception occurred: ", ex);
		ExceptionResponse errorResponse = new ExceptionResponse(ex.getMessage(), request.getDescription(false),
				"500 Internal Server Error", LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
