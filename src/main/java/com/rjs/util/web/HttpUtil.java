package com.rjs.util.web;

import java.nio.charset.StandardCharsets;
import java.util.Set;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * A utility class for creating HttpEntity objects based on headers, request body and credentials.
 */
public class HttpUtil {
	/**
	 * Create a HttpEntity instance.
	 * @param curHeaders The request headers.
	 * @param body       The request body.
	 * @param <E>        The request body type.
	 * @return The HttpEntity instance.
	 */
	public static <E> HttpEntity<E> createHttpEntity(HttpHeaders curHeaders, E body) {
		return createHttpEntity(curHeaders, body, null, null, null);
	}

	/**
	 * Create a HttpEntity instance and includes the content type.
	 * @param curHeaders  The request headers.
	 * @param body        The request body.
	 * @param contentType The request body content type.
	 * @param <E>         The request body type.
	 * @return The HttpEntity instance.
	 */
	public static <E> HttpEntity<E> createHttpEntity(HttpHeaders curHeaders, E body, String contentType) {
		return createHttpEntity(curHeaders, body, contentType, null, null);
	}

	/**
	 * Create a HttpEntity instance and includes basic authorization.
	 * @param curHeaders The request headers.
	 * @param body       The request body.
	 * @param username   The username.
	 * @param password   The password.
	 * @param <E>        The request body type.
	 * @return The HttpEntity instance.
	 */
	public static <E> HttpEntity<E> createHttpEntity(HttpHeaders curHeaders, E body, String username, String password) {
		return createHttpEntity(curHeaders, body, null, username, password);
	}

	/**
	 *
	 * Create a HttpEntity instance and includes the content type and basic authorization.
	 * @param curHeaders  The request headers.
	 * @param body        The request body.
	 * @param contentType The request body content type.
	 * @param username    The username.
	 * @param password    The password.
	 * @param <E>         The request body type.
	 * @return The HttpEntity instance.
	 */
	public static <E> HttpEntity<E> createHttpEntity(HttpHeaders curHeaders, E body, String contentType,
													 String username, String password) {
		HttpHeaders headers = new HttpHeaders();

		if (curHeaders != null) {
			Set<String> keys = curHeaders.keySet();

			for (String key : keys) {
				headers.put(key, curHeaders.get(key));
			}
		}

		if (contentType != null) {
			headers.add("Content-Type", contentType);
		}

		if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
			headers.add("Authorization", "Basic " + encodeForBasicAuth(username, password));
		}

		return body != null ? new HttpEntity<>(body, headers) : new HttpEntity<>(headers);
	}

	/**
	 * Creates the basic authorization encoded value.
	 * @param username The username.
	 * @param password The password.
	 * @return The basic authorization encoded value.
	 */
	public static String encodeForBasicAuth(String username, String password) {
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return null;
		}

		String auth = username + ":" + password;

		return new String(Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII)));
	}
}
