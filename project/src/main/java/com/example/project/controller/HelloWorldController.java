package com.example.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping
    public String HelloWorld() {
        return "Hello World";
    }

    @GetMapping("/simplelist")
    public String[] HelloWorldList() {
        return new String[] { "Hello", "World" };
    }

}