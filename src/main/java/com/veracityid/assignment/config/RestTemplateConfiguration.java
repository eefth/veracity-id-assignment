package com.veracityid.assignment.config;

import java.io.IOException;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {
	
	@Bean
    public RestTemplate restTemplateForGoogleApi()
    {

        HttpClientBuilder clientBuilder = HttpClientBuilder.create();

        CloseableHttpClient client = clientBuilder.build();

        // create a request factory
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(client);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        // error handler to deal with errors from Google Api
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        
        return restTemplate;
    }
	
	/**
     * Error handler for responses from CryptoKit
     */
    private class RestTemplateResponseErrorHandler implements ResponseErrorHandler
    {
        private final Logger log = LoggerFactory.getLogger(RestTemplateResponseErrorHandler.class);

        DefaultResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

        @Override
        public boolean hasError(ClientHttpResponse httpResponse) throws IOException
        {
            return errorHandler.hasError(httpResponse);
        }

        @Override
        public void handleError(ClientHttpResponse httpResponse) throws IOException
        {
            // raw status code is safe against non-standard status codes
            int statusCode = httpResponse.getRawStatusCode();

            if (statusCode == HttpStatus.UNAUTHORIZED.value())
            {
                throw new RuntimeException("Could not authenticate");
            }

            log.error("CryptoKit returned with an error response: {} {}", statusCode, httpResponse.getStatusText());

            String googleApiError = "Unknown Google Api error";

            throw new RuntimeException(googleApiError);
        }
    }
	
}
