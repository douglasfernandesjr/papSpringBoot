package com.example.demo.service;

import com.example.demo.security.service.TokenAuthenticationService;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class TokenProvider {

    public static MockHttpServletRequestBuilder withAuthorization(MockHttpServletRequestBuilder builder) {
        return builder.header("Authorization", "Bearer " + TokenAuthenticationService.addAuthentication("TestUser"));
    }
}
