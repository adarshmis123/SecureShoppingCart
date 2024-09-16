package com.cg.exception;

import java.time.LocalDate;

public class ExceptionResponse {
	private String message;
	private String details;
	private String httpCodeMessage;
	private LocalDate timestamp;
	
	public ExceptionResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ExceptionResponse(String message, String details, String httpCodeMessage, LocalDate timestamp) {
		super();
		this.message = message;
		this.details = details;
		this.httpCodeMessage = httpCodeMessage;
		this.timestamp = timestamp;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getHttpCodeMessage() {
		return httpCodeMessage;
	}
	public void setHttpCodeMessage(String httpCodeMessage) {
		this.httpCodeMessage = httpCodeMessage;
	}
	public LocalDate getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDate timestamp) {
		this.timestamp = timestamp;
	}
		
}
