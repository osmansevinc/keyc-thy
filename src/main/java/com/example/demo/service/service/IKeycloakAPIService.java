package com.example.demo.service.service;

import com.example.demo.data.UserCredential;
import com.example.demo.data.UserRegisterDTO;

public interface IKeycloakAPIService {

	public String getAccessToken(UserCredential user) throws Exception;

	public int registerNewUserAccount(UserRegisterDTO user)  throws Exception;
	
}
