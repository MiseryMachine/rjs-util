package com.rjs.util.web;

import java.io.IOException;

/**
 * Represents an error that may happen when calling a remote service.
 */
public class RemoteServiceException extends IOException {
	public RemoteServiceException() {
	}

	public RemoteServiceException(String message) {
		super(message);
	}

	public RemoteServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public RemoteServiceException(Throwable cause) {
		super(cause);
	}
}
