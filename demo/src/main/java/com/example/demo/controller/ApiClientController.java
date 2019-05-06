package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.entities.Client;
import com.example.demo.model.projections.ClientProjection;
import com.example.demo.model.request.ClientCreateRequest;
import com.example.demo.model.request.ClientUpdateRequest;
import com.example.demo.service.ClientService;

@RestController
@RequestMapping("/api/client")
public class ApiClientController {

	private final ProjectionFactory projectionFactory;
	private final ClientService clientService;

	@Autowired
	public ApiClientController(ProjectionFactory projectionFactory,ClientService clientService) {
		this.clientService = clientService;
		this.projectionFactory = projectionFactory;
	}

	@GetMapping("{id}")
	public ResponseEntity<ClientProjection> findById(@PathVariable("id") Long id) {
		Client client = clientService.findById(id);
		return ResponseEntity.ok(convertToProjection(client));
	}

	@GetMapping
	public ResponseEntity<Page<ClientProjection>> findByFilter(Pageable pageable) {
		Page<Client> page = clientService.findAll(pageable);
		List<ClientProjection> responseList = page.getContent().stream()
				.map(this::convertToProjection)
				.collect(Collectors.toList());

		Page<ClientProjection> responsePage = new PageImpl<>(responseList, pageable, page.getTotalElements());
		return ResponseEntity.ok(responsePage);
	}

	@PutMapping("{id}")
	public ResponseEntity<ClientProjection> update(@PathVariable("id") Long id, @RequestBody ClientUpdateRequest request) {
		request.setId(id);
		Client updated = clientService.update(request);
		return ResponseEntity.ok(convertToProjection(updated));
	}

	@PostMapping
	public ResponseEntity<ClientProjection> create (@RequestBody ClientCreateRequest request) {
		Client updated = clientService.createNew(request);
		return ResponseEntity.ok(convertToProjection(updated));
	}


	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long employeeId) {
		clientService.delete(employeeId);
		return ResponseEntity.noContent().build();
	}

	private ClientProjection convertToProjection(Client client) {
		return projectionFactory.createProjection(ClientProjection.class, client);
	}
}
