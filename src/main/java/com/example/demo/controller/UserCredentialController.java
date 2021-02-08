package com.example.demo.controller;

import javax.validation.Valid;
import javax.ws.rs.NotAuthorizedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.data.UserCredential;
import com.example.demo.data.UserRegisterDTO;
import com.example.demo.exception.TokenNotFoundException;
import com.example.demo.service.service.IKeycloakAPIService;

@RestController
public class UserCredentialController {

	private static final Logger logger = LoggerFactory.getLogger(UserCredentialController.class);

	@Autowired
	private IKeycloakAPIService keycloakService;

	@PostMapping("token")
	public String getToken(@Valid @RequestBody UserCredential user) throws Exception {
		String token = "";
		try {
			token = this.keycloakService.getAccessToken(user);
			if (token == null || "".equals(token))
				throw new TokenNotFoundException("User Login Failed");
		} catch (NotAuthorizedException e) {
			logger.error("[UserCredentialController][getToken][NotAuthorizedException][error] : {}", e);
			throw new TokenNotFoundException("User Login Failed");
		} catch (Exception e) {
			logger.error("[UserCredentialController][getToken][Exception][error] : {}", e);
			throw new Exception();
		}
		return token;
	}

	@PostMapping("registerNewUserAccount")
	public ResponseEntity registerNewUserAccount(@Valid @RequestBody UserRegisterDTO user) throws Exception {
		int result = 0;
		try {
			result = keycloakService.registerNewUserAccount(user);
		} catch (Exception e) {
			throw new Exception();
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
