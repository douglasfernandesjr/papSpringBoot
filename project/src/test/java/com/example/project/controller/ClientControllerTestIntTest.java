package com.example.project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.project.domain.dto.request.ClientCreateRequest;
import com.example.project.domain.dto.response.ClientResponse;
import com.example.project.domain.entities.Client;
import com.example.project.repository.ClientRepository;
import com.example.project.utils.IntegrationTestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * ClientControllerTestIntTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = IntegrationTestConfig.appProperties)
@ActiveProfiles("test")
public class ClientControllerTestIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void should_getEmptyList_whenGetEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/clients")) // Executa
                .andDo(MockMvcResultHandlers.print()) // pega resultado
                .andExpect(MockMvcResultMatchers.status().isOk()) // faz a validação.
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void should_get404_whenGetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/clients/1")) // Executa
                .andDo(MockMvcResultHandlers.print()) // pega resultado
                .andExpect(MockMvcResultMatchers.status().isNotFound()); // faz a validação.
    }

    @Test
    public void should_get_whenGetById() throws Exception {
        Client model = Client.builder().name("nome").phone("987654321").build();
        clientRepository.saveAndFlush(model);

        mockMvc.perform(MockMvcRequestBuilders.get("/clients/"+model.getId())) // Executa
                .andDo(MockMvcResultHandlers.print()) // pega resultado
                .andExpect(MockMvcResultMatchers.status().isOk()); // faz a validação.
    }

    @Test
    public void should_return400_whenPostInvalid() throws Exception {
        // given
        ClientCreateRequest request = ClientCreateRequest.builder()//
                .name("Name").phone("phone").build();

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.post("/clients") //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(request))) // Executa
                .andDo(MockMvcResultHandlers.print()) // pega resultado
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); // faz a validação.
    }

    @Test
    public void should_return400_whenPostInvalid2() throws Exception {
        // given
        ClientCreateRequest request = ClientCreateRequest.builder()//
                .name(null).phone("phone").build();

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.post("/clients") //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(request))) // Executa
                .andDo(MockMvcResultHandlers.print()) // pega resultado
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); // faz a validação.
    }

    @Test
    public void should_create_whenPostValid() throws Exception {
        // given
        ClientCreateRequest request = ClientCreateRequest.builder() //
                .name("Nome").phone("987654321").build();

        // when + then
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/clients") //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(request))) // Executa
                .andDo(MockMvcResultHandlers.print()) // pega resultado
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn(); // faz a validação.

        ClientResponse response = mapper.readValue(result.getResponse().getContentAsString(), ClientResponse.class);

        assertNotNull(response.getId());
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getPhone(), response.getPhone());
    }
}