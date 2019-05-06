package com.example.demo.controller;

import javax.validation.Valid;

import com.example.demo.model.request.ClientCreateRequest;
import com.example.demo.service.ClientService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClientController {

	private final ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@GetMapping
	public String list(Model model) {
		model.addAttribute("clients", clientService.findAll());
		return "client";
	}

	@GetMapping("/client/add")
	public String add(ClientCreateRequest client,Model model) {
		model.addAttribute("client", client);

		return "clientAdd";
	}


	@PostMapping("/client/add")
	public String save(@ModelAttribute("client") @Valid ClientCreateRequest client, BindingResult result,  Model model) {

		if(result.hasErrors()) {
			//model.addAttribute("client", client);
			return "clientAdd";
		}

		clientService.createNew(client);

		return "redirect:/";
	}
}