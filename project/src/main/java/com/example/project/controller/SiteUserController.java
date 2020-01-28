package com.example.project.controller;

import java.net.URI;

import javax.validation.Valid;

import com.example.project.domain.dto.request.UserCreateRequest;
import com.example.project.domain.entities.SiteUser;
import com.example.project.service.SiteUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SiteUserController
 */
@RestController
@RequestMapping("/user")
public class SiteUserController {

    private final SiteUserService service;

    @Autowired
    public SiteUserController(SiteUserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> post(@Valid @RequestBody UserCreateRequest model) {

        SiteUser usr = service.createUser(model.getEmail(), model.getPassword(), model.getIsAdmin());

        return ResponseEntity.created(URI.create("/user/" + usr.getId())).build();
    }
  
}