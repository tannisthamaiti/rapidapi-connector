package org.mule.extension.rapidapi.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.io.InputStream;


import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.dsl.xml.ParameterDsl;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.metadata.fixed.OutputJsonType;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.http.api.HttpConstants.Method;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;


/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class RapidapiOperations {
	
	private static final String QUERY_PARAMETERS_TAB = "Query Parameters";

  /**
   * Example of an operation that uses the configuration and a connection instance to perform some action.
   */
  @MediaType(value = ANY, strict = false)
  public String retrieveInfo(@Config RapidapiConfiguration configuration, @Connection RapidapiConnection connection){
    return "Using Configuration ["  + "] with Connection id [" +  "]";
  }

  /**
   * Example of a simple operation that receives a string parameter and returns a new string message that will be set on the payload.
   */
  @MediaType(value = ANY, strict = false)
  public String sayHi(String person) {
    return buildHelloMessage(person);
  }

 
  
	@DisplayName("Automations - List automations")
	@Summary("Get a summary of an account's Automations.")
	@MediaType(value = "application/json")
	
	public InputStream listAutomations(
		@Connection RapidapiConnection connection,
		@DisplayName("page") int page,
		@DisplayName("per_page") int per_page

		


		
	) {

		String strUri = connection.getStrUri() ;
		String page1 = Integer.toString(page);
		String per_page1 = Integer.toString(per_page);
		

		

		return HttpCallService.call(connection, Method.GET, strUri, null, null,page1,per_page1);
	}
	 /**
	   * Private Methods are not exposed as operations
	   */
	  private String buildHelloMessage(String person) {
	    return "Hello " + person + "!!!";
	  }
}
