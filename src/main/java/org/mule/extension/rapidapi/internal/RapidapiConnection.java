package org.mule.extension.rapidapi.internal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.http.api.HttpConstants.Method;
import org.mule.runtime.http.api.client.HttpClient;

import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public final class RapidapiConnection {
	private static final Logger logger = LoggerFactory.getLogger(RapidapiConnection.class);

	private HttpClient httpClient;
	private String strUri;
	private String apikey;
	private String host;
	
	private int timeout;
	private TimeUnit timeoutUnit;
	
	public RapidapiConnection(HttpClient httpClient, RapidapiConnectionConfiguration config) {

		this.strUri = "https://free-nba.p.rapidapi.com/players";
		//this.strCredentials =  Base64.getEncoder().encodeToString(("x-rapidapi-key:"+config.getApiKey()).getBytes(StandardCharsets.UTF_8));
		this.apikey = config.getApiKey();
		this.host = config.getHost();
		this.timeout = config.getTimeout();
		this.timeoutUnit = config.getTimeoutUnit();
		this.httpClient = httpClient;
	}
	
	public boolean isConnected() throws ConnectionException, IOException, TimeoutException {
		HttpRequest request = HttpRequest.builder()
				.method(Method.GET)
				.uri(strUri)
				.addHeader("x-rapidapi-key", apikey)
				.addHeader("x-rapidapi-host", host)
				.build();

		@SuppressWarnings("deprecation")
		HttpResponse httpResponse = httpClient.send(request, getTimeoutAsMilliseconds(), false, null);
		System.out.println(httpResponse.getStatusCode());
		if (httpResponse.getStatusCode() >= 200 ) {
			return true;
		} else {
			ConnectionException e =  new ConnectionException(
					"Error connecting to the server: Error Code " + httpResponse.getStatusCode() + "~" + httpResponse);
			logger.error("Connection error occurred in MailchimpConnection::isConnected", e);
			throw e;
		}
	}
	public int getTimeoutAsMilliseconds(){
		return (int)TimeUnit.MILLISECONDS.convert(getTimeout(), getTimeoutUnit());
	}
	public int getTimeout() {
		return timeout;
	}

	public TimeUnit getTimeoutUnit() {
		return timeoutUnit;
	}
	

	public String getStrCredentialsKey() {
		return apikey;
	}
	public String getStrCredentialsHost() {
		return host;
	}
	
	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}

  //private final String id;

	/*
	 * public RapidapiConnection(String id) { this.id = id; }
	 * 
	 * public String getId() { return id; }
	 */

  public RapidapiConnection(String string) {
		// TODO Auto-generated constructor stub
	}


public void invalidate() {
    // do something to invalidate this connection!
  }







public String getStrUri() {
	// TODO Auto-generated method stub
	return strUri;
}
}
