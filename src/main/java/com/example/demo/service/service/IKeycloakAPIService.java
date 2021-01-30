package com.example.demo.service.service;

import com.example.demo.data.UserCredential;

public interface IKeycloakAPIService {

	public String getAccessToken(UserCredential user);
	
}
