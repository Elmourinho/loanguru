package com.elmar.loanguru.error;

import org.springframework.http.HttpStatus;

public enum Errors implements ErrorResponse{

	USER_NOT_FOUND("USER_NOT_FOUND", HttpStatus.NOT_FOUND, "User with id {id} not found"),
	LOAN_NOT_FOUND("LOAN_NOT_FOUND", HttpStatus.NOT_FOUND, "Loan with id {id} not found"),
	EXCEEDS_MAX_AMOUNT("EXCEEDS_MAX_AMOUNT", HttpStatus.FORBIDDEN, "Exceeds loan max possible amount");

	String key;
	HttpStatus httpStatus;
	String message;

	Errors(String key, HttpStatus httpStatus, String message) {
		this.message = message;
		this.key = key;
		this.httpStatus = httpStatus;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
