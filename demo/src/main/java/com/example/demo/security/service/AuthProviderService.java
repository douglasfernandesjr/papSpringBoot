package com.example.demo.security.service;

import java.util.Collections;

import com.example.demo.model.entities.User;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthProviderService implements AuthenticationProvider {

  @Autowired
  private UserService userService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    String name = authentication.getName();
    String password = authentication.getCredentials().toString();

    User validUser = userService.ValidateUser(name, password);

    if (validUser != null) {
      return new UsernamePasswordAuthenticationToken(validUser.getUsername(), validUser.getPassword(), Collections.emptyList());
    }

    throw new UsernameNotFoundException("Login e/ou Senha inválidos.");

  }

  @Override
  public boolean supports(Class<?> auth) {
    return auth.equals(UsernamePasswordAuthenticationToken.class);
  }
}