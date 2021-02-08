package com.example.demo.data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserCredential {
	
	@Email(message = "Invalid E-mail!")
	@NotEmpty(message = "This field cannot be empty!")
	private String email;
	
	@NotEmpty(message = "This field cannot be empty!")
	@Size(min=4, max = 20, message = "Your password must be at least 4, at most 20 characters!")
	private String password;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
