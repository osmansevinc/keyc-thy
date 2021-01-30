package com.example.demo.service.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public interface IRestService {
	
	public <T,S> ResponseEntity<S> exhangeMessage(HttpEntity<T> request, Class<S> respClass , HttpMethod hMeth, String serviceURL);
	
}
