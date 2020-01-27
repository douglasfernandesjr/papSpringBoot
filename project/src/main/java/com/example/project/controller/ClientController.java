package com.example.project.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.project.domain.dto.request.ClientCreateRequest;
import com.example.project.domain.dto.response.ClientResponse;
import com.example.project.domain.entities.Client;
import com.example.project.domain.mapper.ClientMapper;
import com.example.project.service.ClientService;
import com.example.project.utils.SiteRoles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientController {

	private final ClientService clientService;
	private final ClientMapper mapper;

	@Autowired
	public ClientController(ClientService clientService, ClientMapper clientMapper) {
		this.clientService = clientService;
		this.mapper = clientMapper;
	}

	@Secured({SiteRoles.APP_USER})
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClientResponse> getById(@PathVariable Integer id) {
		return ResponseEntity.ok(mapper.toDto(clientService.findById(id)));
	}
	@Secured({SiteRoles.APP_USER})
	@GetMapping(value = "/phone")
	public ResponseEntity<List<ClientResponse>> list(@RequestParam("q") String phone) {
		return ResponseEntity.ok(clientService.listByPhone(phone).stream() //
				.map(x -> mapper.toDto(x)) //
				.collect(Collectors.toList()));
	}

	@Secured({SiteRoles.APP_USER})
	@GetMapping
	public ResponseEntity<List<ClientResponse>> list() {
		return ResponseEntity.ok(clientService.listClient().stream() //
				.map(x -> mapper.toDto(x)) //
				.collect(Collectors.toList()));
	}
	@Secured({SiteRoles.APP_USER})
	@GetMapping(value = "/distinct")
	public ResponseEntity<List<String>> listDistinct() {
		return ResponseEntity.ok(clientService.listDistinct());
	}

	@Secured({SiteRoles.APP_ADMIN})
	@PostMapping
	public ResponseEntity<ClientResponse> post(@Valid @RequestBody ClientCreateRequest model) {

		Client client = clientService.createClient(mapper.fromDto(model));

		return ResponseEntity.ok(mapper.toDto(client));
	}

}