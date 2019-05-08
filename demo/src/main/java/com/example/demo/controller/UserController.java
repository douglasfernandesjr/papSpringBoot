package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.entities.User;
import com.example.demo.model.request.UserCreateRequest;
import com.example.demo.service.UserService;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public String add(UserCreateRequest user, Model model) {
		model.addAttribute("user", user);

		return "userAdd";
	}

	@PostMapping("/")
	public String save(@ModelAttribute("user") @Valid UserCreateRequest user, BindingResult result,  Model model) {

		if(result.hasErrors()) {
			return "userAdd";
		}

		User finalUser = userService.CreateUser(user);

		if(finalUser == null)
			result.addError( new ObjectError("User","Já existe um usuário com este nome"));
		else {
			user = new UserCreateRequest();
			model.addAttribute("user", user);
		}
			

		return "userAdd";
	}
}
