package org.mule.extension.rapidapi.internal;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.RefName;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.api.lifecycle.Startable;
import org.mule.runtime.api.lifecycle.Stoppable;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.api.connection.ConnectionProvider;

import javax.inject.Inject;


import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Password;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.error.MuleErrors;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class (as it's name implies) provides connection instances and the funcionality to disconnect and validate those
 * connections.
 * <p>
 * All connection related parameters (values required in order to create a connection) must be
 * declared in the connection providers.
 * <p>
 * This particular example is a {@link PoolingConnectionProvider} which declares that connections resolved by this provider
 * will be pooled and reused. There are other implementations like {@link CachedConnectionProvider} which lazily creates and
 * caches connections or simply {@link ConnectionProvider} if you want a new connection each time something requires one.
 */
public class RapidapiConnectionProvider implements CachedConnectionProvider<RapidapiConnection>, Startable, Stoppable {

	private static final Logger logger = LoggerFactory.getLogger(RapidapiConnectionProvider.class);

	@Inject
	private HttpService httpService;

	private HttpClient httpClient;

	@RefName
	private String configName;

	@ParameterGroup(name = "Connection")
	RapidapiConnectionConfiguration connectionConfiguration;


 

  

  
  
  @Override
	public void start() {
		HttpClientConfiguration httpClientConfiguration = new HttpClientConfiguration.Builder()
				.setName(configName)
				.setProxyConfig(connectionConfiguration.getProxyConfig())
				.build();
		httpClient = httpService.getClientFactory().create(httpClientConfiguration);
		httpClient.start();
	}

	@Override
	public void stop() {
		if (httpClient != null) {
			httpClient.stop();
		}
	}

	@Override
	public RapidapiConnection connect() {
		return new RapidapiConnection(httpClient, connectionConfiguration);
	}

	@Override
	public void disconnect(RapidapiConnection connection) {
		connection.setHttpClient(null);
	}

	@Override
	public ConnectionValidationResult validate(RapidapiConnection connection) {
		ConnectionValidationResult result;
		try {
			connection.isConnected();
			result = ConnectionValidationResult.success();
		} catch (Exception e) {
			logger.error("Connection error occurred in MailchimpConnectionProvider::validate", e);
			result = ConnectionValidationResult.failure("Connection failure",e);
			 	
		}
		return result;
	}
}

