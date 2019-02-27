package com.rjs.util.web;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * A helper class for consuming (calling) web services.
 */
public class RestClientService {
	private RestTemplate restTemplate;

	public RestClientService() {
	}

	public RestClientService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * Consumes a web service.
	 * @param url          The web service URL.
	 * @param method       The web service method (GET, POST, etc).
	 * @param headers      The request headers.
	 * @param request      The request body.
	 * @param responseType The class type of the expected response.
	 * @param uriParams    The URI parameters.
	 * @param <REQ>        The type of the request body.
	 * @param <RES>        The type of the response.
	 * @return The response entity returned from the web service call.  This does not mean the call was successful.
	 * The status code determines that.
	 * @throws WebServiceException If there were any errors during the web service call.
	 */
	public <REQ, RES> ResponseEntity<RES> exchange(String url, HttpMethod method, HttpHeaders headers, REQ request,
												   Class<RES> responseType, Map<String, Object> uriParams) throws WebServiceException {
		HttpEntity<REQ> httpEntity = HttpUtil.createHttpEntity(headers, request);

		return exchange(httpEntity, url, method, responseType, uriParams);
	}

	/**
	 * Consumes a web service using basic authentication.
	 * @param url          The web service URL.
	 * @param method       The web service method (GET, POST, etc).
	 * @param username     The username.
	 * @param password     The password.
	 * @param headers      The request headers.
	 * @param request      The request body.
	 * @param responseType The class type of the expected response.
	 * @param uriParams    The URI parameters.
	 * @param <REQ>        The type of the request body.
	 * @param <RES>        The type of the response.
	 * @return The response entity returned from the web service call.  This does not mean the call was successful.
	 * The status code determines that.
	 * @throws WebServiceException If there were any errors during the web service call.
	 */
	public <REQ, RES> ResponseEntity<RES> exchangeBasicAuth(String url, HttpMethod method, String username, String password,
														HttpHeaders headers, REQ request,
														Class<RES> responseType, Map<String, Object> uriParams) throws WebServiceException {

		HttpEntity<REQ> httpEntity = HttpUtil.createHttpEntity(headers, request, username, password);

		return exchange(httpEntity, url, method, responseType, uriParams);
	}

	/**
	 * Consumes a web service.
	 * @param httpEntity   The http entity.
	 * @param url          The web service URL.
	 * @param method       The web service method (GET, POST, etc).
	 * @param responseType The class type of the expected response.
	 * @param uriParams    The URI parameters.
	 * @param <REQ>        The type of the request body.
	 * @param <RES>        The type of the response.
	 * @return The response entity returned from the web service call.  This does not mean the call was successful.
	 * The status code determines that.
	 * @throws WebServiceException If there were any errors during the web service call.
	 */
	public <REQ, RES> ResponseEntity<RES> exchange(HttpEntity<REQ> httpEntity, String url, HttpMethod method,
													Class<RES> responseType, Map<String, Object> uriParams) throws WebServiceException {
		WebServiceErrorHandler errorHandler = new WebServiceErrorHandler(url);
		RestTemplate rt = restTemplate != null ? restTemplate : new RestTemplate();
		rt.setErrorHandler(errorHandler);

		try {
			if (uriParams != null && !uriParams.isEmpty()) {
				return rt.exchange(url, method, httpEntity, responseType, uriParams);
			}
			else {
				return rt.exchange(url, method, httpEntity, responseType);
			}
		}
		catch (RestClientException e) {
			HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
			String message = "Web service error calling " + url + ": " + status.getReasonPhrase() + " (" + status.toString() + ")\nCause: " + e.getMessage();

			throw new WebServiceException(message, e, status);
		}
	}
	public <REQ, RES> ResponseEntity<RES> exchange(HttpEntity<REQ> httpEntity, String url, HttpMethod method,
												   ParameterizedTypeReference<RES> parameterizedTypeReference,
												   Map<String, Object> uriParams) throws WebServiceException {
		WebServiceErrorHandler errorHandler = new WebServiceErrorHandler(url);
		RestTemplate rt = restTemplate != null ? restTemplate : new RestTemplate();
		rt.setErrorHandler(errorHandler);

		try {
			if (uriParams != null && !uriParams.isEmpty()) {
				return rt.exchange(url, method, httpEntity, parameterizedTypeReference, uriParams);
			}
			else {
				return rt.exchange(url, method, httpEntity, parameterizedTypeReference);
			}
		}
		catch (RestClientException e) {
			HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
			String message = "Web service error calling " + url + ": " + status.getReasonPhrase() + " (" + status.toString() + ")\nCause: " + e.getMessage();

			throw new WebServiceException(message, e, status);
		}
	}
}
