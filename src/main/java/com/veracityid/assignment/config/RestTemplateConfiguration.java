package com.veracityid.assignment.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {
	
	@Bean
    public RestTemplate restTemplateForGoogleApi()
    {
//        CryptoKitDetails details = config.getCryptoKitDetails();
//
//        if (isBlank(details.getBaseUrl()))
//        {
//            return null;
//        }

        // set the ssl context
//        SSLConnectionSocketFactory sslConnectionSocketFactory = null;
//
//        SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
//        sslContextBuilder.setProtocol(SSLConnectionSocketFactory.TLS);
//
//        try (FileInputStream fis = new FileInputStream(details.getKeystoreLocation()))
//        {
//            KeyStore clientStore = KeyStore.getInstance(details.getKeystoreType());
//            clientStore.load(fis, details.getKeystorePassword().toCharArray());
//
//            sslContextBuilder.loadKeyMaterial(clientStore, details.getKeystorePassword().toCharArray());
//
//            sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build());
//        }
//        catch (Exception e)
//        {
//            LOG.error("Error in CryptoKit keystore: {}", e.getMessage(), e);
//
//            throw new DpcException(message.getCryptoKitKeystoreError(), TivoliMarkers.CRYPTOKIT, e.getMessage());
//        }

        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
//        clientBuilder.setSSLSocketFactory(sslConnectionSocketFactory);
//        clientBuilder.useSystemProperties();
//        if (StringUtils.isNotBlank(config.getProxyHost()))
//        {
//            clientBuilder.setProxy(new HttpHost(config.getProxyHost(), config.getProxyPort()));
//        }

        CloseableHttpClient client = clientBuilder.build();


        // create a request factory
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(client);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        // error handler to deal with errors from CryptoKit
        //restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        //restTemplate.setInterceptors(Collections.singletonList(new RequestLoggingInterceptor(LOG, false)));

        return restTemplate;
    }
	
}
