package com.rjs.util.web;

import org.springframework.http.HttpStatus;

/**
 * Represents an error that may happen when calling a remote service.  This includes the HTTP status code returned by
 * the called service.
 */
public class WebServiceException extends RemoteServiceException {
	private final HttpStatus status;

	public WebServiceException() {
		status = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public WebServiceException(String message) {
		super(message);
		status = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public WebServiceException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	public WebServiceException(Throwable cause) {
		super(cause);
		status = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public WebServiceException(Throwable cause, HttpStatus status) {
		super(cause);
		this.status = status;
	}

	public WebServiceException(String message, Throwable cause) {
		super(message, cause);
		status = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public WebServiceException(String message, Throwable cause, HttpStatus status) {
		super(message, cause);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}
}
