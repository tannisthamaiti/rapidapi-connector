package org.mule.extension.rapidapi.internal;

import java.util.concurrent.TimeUnit;


import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Password;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.http.api.client.proxy.ProxyConfig;


@Configuration(name = "connectionConfig")
@ConnectionProviders(RapidapiConnectionProvider.class)
public class RapidapiConnectionConfiguration {
	
	
	    @Parameter
	    @DisplayName("x-rapidapi-host")
	    @Placement(order = 1)
	    @Expression(ExpressionSupport.SUPPORTED)
	    private String Host;

	    @Parameter
	    @DisplayName("x-rapidapi-key")
	    @Placement(order = 2)
	    @Expression(ExpressionSupport.SUPPORTED)
	    private String apiKey;

	    @Parameter
	    @Optional(defaultValue = "5")
	    @DisplayName("Timeout")
	    @Placement(tab = Placement.ADVANCED_TAB, order = 1)
	    @Expression(ExpressionSupport.SUPPORTED)
	    private int timeout;

	    @Parameter
	    @Optional(defaultValue = "SECONDS")
	    @DisplayName("Timeout unit")
	    @Placement(tab = Placement.ADVANCED_TAB, order = 2)
	    @Expression(ExpressionSupport.SUPPORTED)
	    private TimeUnit timeoutUnit;

	    @Parameter
	    @Optional
	    @DisplayName("Proxy configuration")
	    @Placement(tab = "Proxy", order = 3)
	    private ProxyConfig proxyConfig;

	    public String getHost() {
	        return Host;
	    }

	    public String getApiKey() {
	        return apiKey;
	    }

	    public int getTimeout() {
	        return timeout;
	    }

	    public TimeUnit getTimeoutUnit() {
	        return timeoutUnit;
	    }

	    public ProxyConfig getProxyConfig() {
	        return proxyConfig;
	    }

	}



