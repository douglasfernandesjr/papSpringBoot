package com.example.project.security;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.project.domain.dto.request.LoginRequest;
import com.example.project.domain.dto.request.UserCreateRequest;
import com.example.project.domain.dto.request.UserCreateRequestTest;
import com.example.project.service.SiteUserService;
import com.example.project.utils.IntegrationTestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * BaseSecurityTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = IntegrationTestConfig.appProperties)
@ActiveProfiles("test")
public class BaseSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SiteUserService service;

    @Test
    public void should_PublicAcessSwagger() throws Exception {
        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/swagger-ui.html")) //
                .andDo(MockMvcResultHandlers.print()) // pega resultado
                .andExpect(MockMvcResultMatchers.status().isOk()); // faz a validação.
    }

    @Test
    public void should_PrivateAcessGetClients_return403() throws Exception {
        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/clients")) //
                .andDo(MockMvcResultHandlers.print()) // pega resultado
                .andExpect(MockMvcResultMatchers.status().isForbidden()); // faz a validação.
    }

    @Test
    public void should_GenerateToken_forValidUser() throws Exception {

        UserCreateRequest usr = UserCreateRequestTest.usrValidEmail3;
        LoginRequest loginInfo = new LoginRequest(usr.getEmail(), usr.getPassword());

        // given
        this.service.createUser(usr.getEmail(), usr.getPassword(), usr.getIsAdmin());

        // when + then
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")//
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(loginInfo))) // Executa
                .andDo(MockMvcResultHandlers.print()) // pega resultado
                .andExpect(MockMvcResultMatchers.status().isOk()) // faz a validação.
                .andExpect(MockMvcResultMatchers.header().exists("Authorization"))
                .andReturn();


        String authHeader = result.getResponse().getHeader("Authorization");

        assertNotNull(authHeader);

        String[] bearer = authHeader.split(" ");
        assertEquals(bearer[0],"Bearer");
        assertNotNull(bearer[1]);
    }

    @Test
    public void should_NotGenerateToken_forInvalidPassword() throws Exception {

        UserCreateRequest usr = UserCreateRequestTest.usrValidEmail4;
        LoginRequest loginInfo = new LoginRequest(usr.getEmail(), usr.getPassword());

        // given
        this.service.createUser(usr.getEmail(), usr.getPassword() + "ERRADO", usr.getIsAdmin());

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.post("/login")//
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(loginInfo))) // Executa
                .andDo(MockMvcResultHandlers.print()) // pega resultado
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
     
    }

   
}