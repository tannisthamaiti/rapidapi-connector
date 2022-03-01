package org.mule.extension.rapidapi.internal;



import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.io.IOUtils;
import static org.mule.extension.rapidapi.internal.Parse.*;

import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.extension.api.error.MuleErrors;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.request.HttpRequestBuilder;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeoutException;



public class HttpCallService {

    private static final Logger logger = LoggerFactory.getLogger(HttpCallService.class);

    private HttpCallService() { }
    private HttpClient client;

    @SuppressWarnings({ "deprecation", "unused" })
	public static InputStream call(RapidapiConnection connection, HttpConstants.Method method, String strUri,
                                   MultiMap<String, String> queryParams, Object body, String page, String per_page)  {

        int timeout = connection.getTimeoutAsMilliseconds();

        String strCredentialsKey = connection.getStrCredentialsKey();
        String strCredentialsHost = connection.getStrCredentialsHost();
        HttpClient client = connection.getHttpClient();

        HttpRequestBuilder builder = HttpRequest.builder()
                .method(method)
                .uri(strUri)
                .addHeader("x-rapidapi-key", strCredentialsKey)
				.addHeader("x-rapidapi-host", strCredentialsHost)
				.addQueryParam("page",page)
				.addQueryParam("per_page", per_page);


        if(body != null) {
            try {
                builder.entity(objectToEntity(body));
            }  catch (JsonProcessingException e) {
                logger.error("JSON Parsing error occurred in HttpCallService::call", e);
                
            }
        }

       

        HttpRequest request = builder.build();
        if (logger.isDebugEnabled()){
            logger.debug(request.toString());
        }
        System.out.println(request.toString());
        System.out.println(client);

        HttpResponse httpResponse;
        InputStream httpResponseStream;
        byte[] response = null;

        try {
            httpResponse = client.send(request);
            httpResponseStream = httpResponse.getEntity().getContent();

            response = IOUtils.toByteArray(httpResponseStream);
            InputStream copy = new ByteArrayInputStream(response);
            logger.debug(getHttpResponse(copy));

            int statusCode = httpResponse.getStatusCode();
            if (statusCode >= 300) {
                statusCode = statusCode - statusCode%100;

                String reasonPhrase = httpResponse.getReasonPhrase();
                String httpResponseStr;
                httpResponseStr = getHttpResponse(httpResponseStream);

               
            }

        } catch (IOException e) {
            logger.error("Connection error occurred in HttpCallService::call", e);
            
        } catch (TimeoutException e) {
            logger.error("Timeout error occurred in HttpCallService::call", e);
            
        }

        return new ByteArrayInputStream(response);
    }

    private static String getHttpResponse(InputStream httpResponseStream) {
        String httpResponseStr;
        try {
            httpResponseStr = inputStreamToString(httpResponseStream);
        } catch (IOException e) {
            logger.error("Error occurred in HttpCallService::getHttpResponse", e);
            httpResponseStr = "There was an error parsing the reason phrase or it was empty.";
        }
        return httpResponseStr;
    }

   

    private static String sanitize(String queryParam){
        //Sanitize invalid symbols and spaces (only allowing -, _, . and ,)
        String sanitized = queryParam.replaceAll("([^a-zA-Z0-9_\\-.,])", "");
        //Sanitize values starting with dot, for example: .config
        sanitized = sanitized.replaceAll("(^\\.[a-zA-Z0-9]+)+", "invalid");
        return sanitized;
    }
}
