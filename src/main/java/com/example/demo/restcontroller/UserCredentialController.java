package com.example.demo.restcontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.data.UserCredential;
import com.example.demo.service.service.IKeycloakAPIService;

@RestController
public class UserCredentialController {

	@Autowired
	private IKeycloakAPIService keycloakService;

	@PostMapping("getToken")
	public String getToken(HttpServletRequest request, HttpServletResponse response, @RequestBody UserCredential user) throws Exception {
		String token = "";
		try {
			token = this.keycloakService.getAccessToken(user);
			//response.setHeader("Authorization", "Bearer " + token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

}
