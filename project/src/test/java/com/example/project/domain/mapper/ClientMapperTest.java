package com.example.project.domain.mapper;

import static org.junit.Assert.assertEquals;

import com.example.project.configuration.MapperConfig;
import com.example.project.domain.dto.request.ClientCreateRequest;
import com.example.project.domain.dto.response.ClientResponse;
import com.example.project.domain.entities.Client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

/**
 * ClientMapperTest
 */
@RunWith(MockitoJUnitRunner.class)
public class ClientMapperTest {

    @Spy
    private ModelMapper modelMapper = new MapperConfig().getModelMapper();

    @InjectMocks
    private ClientMapper mapper;

    @Test
    public void shouldConvertClientToClientResponse() {
        Client entity = Client.builder().id(1).name("name").phone("987654321").build();
        ClientResponse dto = mapper.toDto(entity);

        assertEquals("Unexpected value found!", dto.getId(), entity.getId());
        assertEquals("Unexpected value found!", dto.getName(), entity.getName());
        assertEquals("Unexpected value found!", dto.getPhone(), entity.getPhone());
    }

    @Test
    public void shouldConvertClientCreateRequestToClient() {
        ClientCreateRequest dto = ClientCreateRequest.builder().name("name").phone("987654321").build();

        Client entity = mapper.fromDto(dto);

        assertEquals("Unexpected value found!", dto.getName(), entity.getName());
        assertEquals("Unexpected value found!", dto.getPhone(), entity.getPhone());
    }

}