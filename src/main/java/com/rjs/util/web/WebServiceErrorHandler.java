package com.rjs.util.web;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

/**
 * This is used to catch errors during a web service call and make sure they are passed up through the call stack.
 */
public class WebServiceErrorHandler implements ResponseErrorHandler {
	private static final String MSG_FMT = "Web service error calling %s:\n%s (%s)";
	private String url;
	private HttpStatus status;
	private String message;

	public WebServiceErrorHandler(String url) {
		this.url = url;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
		HttpStatus.Series series = clientHttpResponse.getStatusCode().series();

		return series == HttpStatus.Series.CLIENT_ERROR || series == HttpStatus.Series.SERVER_ERROR;
	}

	@Override
	public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
		String serviceReply = clientHttpResponse.getHeaders().getFirst("service_reply");
		status = clientHttpResponse.getStatusCode();

		message = String.format(MSG_FMT, url, clientHttpResponse.getStatusText(), status.toString());

		if (serviceReply != null && !serviceReply.trim().isEmpty()) {
			message += " - " + serviceReply;
		}

		throw new WebServiceException(message, status);
	}
}
