package com.example.project.configuration.security;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.example.project.domain.entities.SiteRole;
import com.example.project.domain.entities.SiteUser;
import com.example.project.service.SiteUserService;

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
    private SiteUserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        SiteUser validUser = userService.ValidateUser(name, password);

        if (validUser != null) {
            List<SiteRole> roles = validUser.getRoles().stream() //
                    .map(x -> x.getSiteRole()).collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(validUser.getEmail(), //
                    validUser.getPassword(), roles);
        } else {
            throw new UsernameNotFoundException("Login e/ou Senha inválidos.");
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}