package com.example.project.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.project.domain.dto.request.ClientCreateRequest;
import com.example.project.domain.dto.response.ClientResponse;
import com.example.project.domain.entities.Client;
import com.example.project.domain.mapper.ClientMapper;
import com.example.project.service.ClientService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * ClientControllerTest
 */
@RunWith(MockitoJUnitRunner.class)
public class ClientControllerTest {

    @Mock
    private ClientMapper mapper;

    @Mock
    private ClientService service;

    @InjectMocks
    private ClientController controller;

    private final Integer id = 1;
    private final String name = "Some string";
    private final String phone = "987654321";

    
    Client entity = Client.builder().id(id).name(name).phone(phone).build();
    
    ClientCreateRequest createDto = ClientCreateRequest.builder().name(name).phone(phone).build();
    
    ClientResponse responseDto = ClientResponse.builder().id(id).name(name).phone(phone).build();


    @Test
    public void should_GetById() {
        //given
        when(service.findById(any())).thenReturn(entity);
        when(mapper.toDto(any())).thenReturn(responseDto);
       
        
        // when
        ResponseEntity<ClientResponse> response = controller.getById(1);
        
        //then
        assertEquals("Deve ser 0k /200", response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void should_Created_WhenInvalid() {
        //given
        when(mapper.fromDto(any())).thenReturn(entity);
        when(service.createClient(any())).thenReturn(entity);
        when(mapper.toDto(any())).thenReturn(responseDto);
        
        // when
        ResponseEntity<ClientResponse> response = controller.post(createDto);
        
        //then
        assertEquals("Deve ser 0k /200", response.getStatusCode(), HttpStatus.OK);
    }
}