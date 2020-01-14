package com.example.project.controller;

import java.util.List;

import com.example.project.domain.dto.request.ClientCreateRequest;
import com.example.project.domain.dto.response.ClientResponse;
import com.example.project.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientController {

	private final ClientService clientService;

	@Autowired
	public ClientController(ClientService clientService) {
		this.clientService = clientService;
    }
    
	@GetMapping
	public List<ClientResponse> list() {
		return clientService.listClient();
    }
    
	@PostMapping
	public ClientResponse post(@RequestBody ClientCreateRequest model) {
		return clientService.createClient(model);
	}

}