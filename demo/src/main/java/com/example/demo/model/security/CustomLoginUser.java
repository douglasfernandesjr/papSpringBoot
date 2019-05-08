package com.example.demo.model.security;

import com.example.demo.model.entities.User;

public class CustomLoginUser {

	private static final long serialVersionUID = 1L;

	public CustomLoginUser(User user) {

		id = user.getId();
		username = user.getUsername();

	}

	private Long id;

	private String username;

	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

}
