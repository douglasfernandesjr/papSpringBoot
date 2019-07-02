package com.example.demo.integration.controller;

import com.example.demo.model.request.ClientCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import br.com.six2six.fixturefactory.Fixture;

import org.springframework.test.context.junit4.SpringRunner;

import static com.example.demo.service.TokenProvider.withAuthorization;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class ApiClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    // write test cases here

    @Test
    public void whenUnauthorized_thenForbidden() throws Exception {
        mockMvc.perform(get("/api/client")).andDo(print()).andExpect(status().is4xxClientError())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void whenInvalidPhone_thenBadRequest() throws Exception {

        ClientCreateRequest request = Fixture.from(ClientCreateRequest.class).gimme("invalid_phone");

        mockMvc.perform(withAuthorization(post("/api/client")).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))).andDo(print()).andExpect(status().isBadRequest());

        ClientCreateRequest request2 = Fixture.from(ClientCreateRequest.class).gimme("invalid_phone_2");

        mockMvc.perform(withAuthorization(post("/api/client")).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request2))).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void whenEmpty_thenBadRequest() throws Exception {

        ClientCreateRequest request = Fixture.from(ClientCreateRequest.class).gimme("invalid_empty");

        mockMvc.perform(withAuthorization(post("/api/client")).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))).andDo(print()).andExpect(status().isBadRequest());

    }

    @Test
    public void whenValid_shouldCreateClient() throws Exception {

        ClientCreateRequest request = Fixture.from(ClientCreateRequest.class).gimme("valid");

        mockMvc.perform(withAuthorization(post("/api/client")).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("id").isNumber());
    }

  
}