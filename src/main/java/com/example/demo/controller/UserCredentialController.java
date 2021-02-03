package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.data.UserCredential;
import com.example.demo.data.UserSingUpDTO;
import com.example.demo.exception.TokenNotFoundException;
import com.example.demo.service.service.IKeycloakAPIService;

@RestController
public class UserCredentialController {

	@Autowired
	private IKeycloakAPIService keycloakService;

	@PostMapping("token")
	public String getToken(@RequestBody UserCredential user) throws Exception {
		String token = "";
		try {
			token = this.keycloakService.getAccessToken(user);
			if(token == null || token.equals("")) 
				throw new TokenNotFoundException("User Login Failed");
		} catch (Exception e) {
			throw new Exception();
		}
		return token;
	}
	
	@PostMapping("signUp")
	public String signUp(@RequestBody UserSingUpDTO user) throws Exception {
		String token = "";
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

}
