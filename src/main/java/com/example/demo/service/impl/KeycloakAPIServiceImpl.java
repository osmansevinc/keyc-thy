package com.example.demo.service.impl;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.example.demo.data.UserCredential;
import com.example.demo.service.service.IKeycloakAPIService;

@Service
public class KeycloakAPIServiceImpl implements IKeycloakAPIService {

	@Autowired
	private Environment env;

	public String getAccessToken(UserCredential user) {
		KeycloakBuilder keycloakBuilder = this.newKeycloakBuilderWithPasswordCredentials(user.getUsername(),
				user.getPassword());
		return keycloakBuilder.build().tokenManager().getAccessToken().getToken();
	}

	private KeycloakBuilder newKeycloakBuilderWithPasswordCredentials(String username, String password) {
		try {
			return KeycloakBuilder.builder() //
					.realm(env.getProperty("keycloak.realm")) //
					.serverUrl(env.getProperty("keycloak.auth-server-url"))//
					.clientId(env.getProperty("keycloak.resource")) //
					.clientSecret(env.getProperty("keycloak.credentials.secret")) //
					.grantType(OAuth2Constants.CLIENT_CREDENTIALS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
