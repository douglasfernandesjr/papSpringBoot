package com.example.demo.model.request;

import javax.validation.constraints.NotBlank;

public class ClientUpdateRequest extends ClientCreateRequest {

	@NotBlank(message = "Id é uma informação obrigatória.")
	private long id;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
