package com.example.demo.data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserRegisterDTO {

	@Size(min = 4, max = 40)
	@NotEmpty
	private String username;

	@Size(min = 1, max = 40)
	@NotEmpty
	private String firstName;

	@Size(min = 1, max = 40)
	@NotEmpty
	private String lastName;

	@Email
	@NotEmpty
	private String email;

	@NotEmpty
	@Size(min = 4, max = 40)
	private String password;

	@NotEmpty
	@Size(min = 4, max = 40)
	private String confirmPassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
