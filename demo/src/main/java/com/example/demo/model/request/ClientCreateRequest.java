package com.example.demo.model.request;

import javax.validation.constraints.NotBlank;

public class ClientCreateRequest {
	
	@NotBlank(message = "Nome é uma informação obrigatória.")
	private String name;
	@NotBlank(message = "Telefone é uma informação obrigatória.")
	private String phone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
