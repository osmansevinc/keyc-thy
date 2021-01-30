package com.example.demo.service.impl;

import com.example.demo.service.service.IRestService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class RestServiceImpl implements IRestService {

	private final static Logger log = LoggerFactory.getLogger(RestServiceImpl.class);

	private RestTemplate restTemplate;

	@PostConstruct
	private void initHttpClientFactory() {
		log.debug("SerdooRestService..");

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		objectMapper.configure(MapperFeature.USE_STD_BEAN_NAMING, true);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.registerModule(new JaxbAnnotationModule());

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

		MediaType fhir = new MediaType("text", "plain", Charset.forName("UTF-8"));
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED));		

		List<MediaType> jacksonTypes = new ArrayList(converter.getSupportedMediaTypes());

		jacksonTypes.add(fhir);

		converter.setSupportedMediaTypes(jacksonTypes);

		converter.setObjectMapper(objectMapper);

		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
				HttpClientBuilder.create().setConnectionManager(new PoolingHttpClientConnectionManager() {
					{
						setDefaultMaxPerRoute(20);
						setMaxTotal(200);
					}
				}).build());

		clientHttpRequestFactory.setConnectTimeout(30000);
		clientHttpRequestFactory.setReadTimeout(5000);
		this.restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(clientHttpRequestFactory));
		// this.restTemplate.getInterceptors().add(new
		// LoggingRequestInterceptor(this.gnlExtSystCallLogRepository));
		this.restTemplate.setMessageConverters(Arrays.asList(converter));
	}

	@Override
	public <T, S> ResponseEntity<S> exhangeMessage(HttpEntity<T> request, Class<S> respClass, HttpMethod hMeth,
			String serviceURL) {
		try {
			return restTemplate.exchange(serviceURL, hMeth, request, respClass);
		} catch (RestClientException e) {
			e.printStackTrace();
			/*
			 * A connection timeout occurs only upon starting the TCP connection. This
			 * usually happens if the remote machine does not answer. This means that the
			 * server has been shut down, you used the wrong IP/DNS name, wrong port or the
			 * network connection to the server is down.
			 */

			/*
			 * A socket timeout is dedicated to monitor the continuous incoming data flow.
			 * If the data flow is interrupted for the specified timeout the connection is
			 * regarded as stalled/broken. Of course this only works with connections where
			 * data is received all the time.
			 */
		}
		catch (Exception e) {
			e.printStackTrace();
			/*
			 * A connection timeout occurs only upon starting the TCP connection. This
			 * usually happens if the remote machine does not answer. This means that the
			 * server has been shut down, you used the wrong IP/DNS name, wrong port or the
			 * network connection to the server is down.
			 */

			/*
			 * A socket timeout is dedicated to monitor the continuous incoming data flow.
			 * If the data flow is interrupted for the specified timeout the connection is
			 * regarded as stalled/broken. Of course this only works with connections where
			 * data is received all the time.
			 */
		}
		return null;
	}

}
