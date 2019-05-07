package com.example.demo.model.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.example.demo.model.entities.User;

public class CustomLoginUser {

	private static final long serialVersionUID = 1L;

	public CustomLoginUser( User user) {

		id = user.getId();
		username = user.getUsername();
		password = user.getPassword();
	}

	private Long id;

	private String username;

	private String password;

	private Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();


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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<GrantedAuthority> getGrantedAuthoritiesList() {
		return grantedAuthoritiesList;
	}
	public void setGrantedAuthoritiesList(Collection<GrantedAuthority> grantedAuthoritiesList) {
		this.grantedAuthoritiesList = grantedAuthoritiesList;
	}

}
