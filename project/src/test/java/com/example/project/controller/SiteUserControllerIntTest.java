package com.example.project.controller;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.project.domain.dto.request.UserCreateRequest;
import com.example.project.domain.dto.request.UserCreateRequestTest;
import com.example.project.domain.entities.SiteUser;
import com.example.project.repository.SiteUserRepository;
import com.example.project.utils.IntegrationTestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * SiteUserControllerIntTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = IntegrationTestConfig.appProperties)
@ActiveProfiles("test")
public class SiteUserControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SiteUserRepository siteUserRepository;

    @Test
    public void should_return400_whenPostInvalidNull() throws Exception {
        // given
        UserCreateRequest request = UserCreateRequestTest.usrInvalidNull;

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.post("/user") //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(request))) // Executa
                .andDo(MockMvcResultHandlers.print()) // pega resultado
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); // faz a validação.
    }

    @Test
    public void should_return400_whenPostInvalidEmail() throws Exception {
        // given
        UserCreateRequest request = UserCreateRequestTest.usrInvalidEmail;

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.post("/user") //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(request))) // Executa
                .andDo(MockMvcResultHandlers.print()) // pega resultado
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); // faz a validação.
    }

    @Test
    public void should_return201_whenCreated() throws Exception {
        // given
        UserCreateRequest request = UserCreateRequestTest.usrValidEmail2;

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.post("/user") //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(request))) // Executa
                .andDo(MockMvcResultHandlers.print()) // pega resultado
                .andExpect(MockMvcResultMatchers.status().isCreated()); // faz a validação.
    }

    @Test
    public void should_return400_whenDuplicatedEmail() throws Exception {
        // given

        UserCreateRequest request = UserCreateRequestTest.usrValidEmail1;

        SiteUser model = SiteUser.builder().email(request.getEmail()).password("987654321").build();
        siteUserRepository.saveAndFlush(model);


        // when + then
        mockMvc.perform(MockMvcRequestBuilders.post("/user") //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(request))) // Executa
                .andDo(MockMvcResultHandlers.print()) // pega resultado
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); // faz a validação.
    }
}