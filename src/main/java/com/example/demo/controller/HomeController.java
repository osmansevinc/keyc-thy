package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class HomeController {


	@GetMapping("/")
	public String helloWorld(Principal principal) {
		return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
	}

	@GetMapping("/signin")
	public String signin() {
		return "signin/signin";
	}

}
