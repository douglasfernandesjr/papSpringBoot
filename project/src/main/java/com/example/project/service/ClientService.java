package com.example.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.project.domain.dto.request.ClientCreateRequest;
import com.example.project.domain.dto.response.ClientResponse;
import com.example.project.domain.entities.Client;
import com.example.project.exception.DataNotFoundException;
import com.example.project.repository.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientResponse createClient(ClientCreateRequest model) {


        Client client = new Client();
        client.setName(model.getName());
        client.setPhone(model.getPhone());

        clientRepository.save(client);


        // NoArgsConstructor
        ClientResponse returnModel = new ClientResponse();

        // Data
        returnModel.setId(client.getId());
        returnModel.setName(client.getName());
        returnModel.setPhone(client.getPhone());

        return returnModel;
    }

    public List<ClientResponse> listClient() {
          return clientRepository.findAll().stream()
                    .map(x -> new ClientResponse(x.getId(),x.getName(),x.getPhone()))
                    .collect(Collectors.toList());
    }

    // public ClientResponse findById(Integer id) {
    //     Optional<Client> client = clientRepository.findById(id);
    //     return client.orElseThrow(() -> new DataNotFoundException("Client Not found"));
    // }

}