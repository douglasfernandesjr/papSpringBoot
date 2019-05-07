package com.example.demo.model.request;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public class UserCreateRequest {

	@NotBlank(message = "Nome é uma informação obrigatória.")
	private String username;

	@NotBlank(message = "Telefone é uma informação obrigatória.")
	private String password;


	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
