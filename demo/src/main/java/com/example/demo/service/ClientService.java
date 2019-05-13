package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.demo.exceptions.DataNotFoundException;
import com.example.demo.model.entities.Client;
import com.example.demo.model.projections.ClientLoginProjection;
import com.example.demo.model.request.ClientCreateRequest;
import com.example.demo.model.request.ClientUpdateRequest;
import com.example.demo.repository.ClientRepository;

@Service
public class ClientService {

	private final ClientRepository clientRepository;

	@Autowired
	public ClientService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	public Client createNew(ClientCreateRequest request) {
		Client clientModel = new Client();
		clientModel.setName(request.getName());
		clientModel.setPhone(request.getPhone());

		clientRepository.save(clientModel);
		return clientModel;
	}

	public Page<Client> findAll(Pageable pageable) {
		return clientRepository.findAll(pageable);
	}

	public Iterable<Client> findAll() {
		return clientRepository.findAll();
	}

	public Client findById(long id) {
		return clientRepository.findById(id).orElseThrow(DataNotFoundException::new);

	}

	public Client update(ClientUpdateRequest request) {
		Client clientModel = clientRepository.findById(request.getId()).orElseThrow(DataNotFoundException::new);
		clientModel.setName(request.getName());
		clientModel.setPhone(request.getPhone());

		clientRepository.save(clientModel);
		return clientModel;
	}

	public void delete(Long id) {
		clientRepository.findById(id).orElseThrow(DataNotFoundException::new);
		clientRepository.deleteById(id);
	}

	public List<ClientLoginProjection> ListClientLoginProjection() {
		return clientRepository.ListClientLogin();
	}

}
