package com.example.project.service;

import java.util.ArrayList;
import java.util.List;

import com.example.project.domain.dto.request.ClientCreateRequest;
import com.example.project.domain.dto.response.ClientResponse;

import org.springframework.stereotype.Service;

@Service
public class ClientService {

    public ClientResponse createClient(ClientCreateRequest model) {

        // NoArgsConstructor
        ClientResponse returnModel = new ClientResponse();

        // Data
        returnModel.setId(1);
        returnModel.setName(model.getName());
        returnModel.setPhone(model.getPhone());

        return returnModel;
    }

    public List<ClientResponse> listClient() {
        List<ClientResponse> list = new ArrayList<ClientResponse>();

        // AllArgsConstructor
        list.add(new ClientResponse(1, "Nome", "119876543"));

        // Builder
        list.add(ClientResponse.builder().id(2).name("Nome").phone("119876543").build());

        return list;
    }

}