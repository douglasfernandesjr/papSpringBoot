package com.example.demo.controller;

import com.example.demo.model.request.ClientCreateRequest;
import com.example.demo.model.request.UserCreateRequest;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.com.six2six.fixturefactory.Fixture;

import static com.example.demo.service.TokenProvider.withAuthorization;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    public ApiClientControllerTest(UserService usrService) {
        super();
        // criando usuário padrão para permitir a autenticação.

        UserCreateRequest usr = new UserCreateRequest();
        usr.setUsername("TestUser");
        usr.setPassword("123456");

        usrService.CreateUser(usr);
    }

    @Test
    void shouldGetError403() throws Exception {
        mockMvc.perform(get("/api/client")).andDo(print()).andExpect(status().is4xxClientError())
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldGetError200() throws Exception {
        mockMvc.perform(withAuthorization(get("/api/client"))).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldCreateAndDeleteClient() throws Exception {

        ClientCreateRequest request = Fixture.from(ClientCreateRequest.class).gimme("valid");

        mockMvc.perform(withAuthorization(post("/api/client")).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("id").isNumber());

        mockMvc.perform(withAuthorization(delete("/api/client/1")).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldValidateClient() throws Exception {

        ClientCreateRequest request = Fixture.from(ClientCreateRequest.class).gimme("invalid_phone");

        mockMvc.perform(withAuthorization(post("/api/client")).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))).andDo(print()).andExpect(status().isBadRequest());

        request = Fixture.from(ClientCreateRequest.class).gimme("invalid_phone_2");

        mockMvc.perform(withAuthorization(post("/api/client")).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))).andDo(print()).andExpect(status().isBadRequest());

        request = Fixture.from(ClientCreateRequest.class).gimme("invalid_empty");

        mockMvc.perform(withAuthorization(post("/api/client")).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))).andDo(print()).andExpect(status().isBadRequest());
    }

}