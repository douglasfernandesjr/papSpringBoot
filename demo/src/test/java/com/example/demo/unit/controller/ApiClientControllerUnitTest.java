package com.example.demo.unit.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.controller.ApiClientController;
import com.example.demo.exceptions.DataNotFoundException;
import com.example.demo.service.ClientService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(ApiClientController.class)
public class ApiClientControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClientService clientService;

    @TestConfiguration
    static class ApiClientControllerUnitTestContextConfiguration {

        @Bean
        public ProjectionFactory projectionFactory() {
            return new SpelAwareProxyProjectionFactory();
        }
    }

    // write test cases here

    @Test
    public void givenClient_whenGetById_thenReturn404() throws Exception {

        Mockito.when(clientService.findById(10)).thenThrow(DataNotFoundException.class);

        mvc.perform(get("/api/client/10").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }
}