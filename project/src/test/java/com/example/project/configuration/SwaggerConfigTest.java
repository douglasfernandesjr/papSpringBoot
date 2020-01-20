package com.example.project.configuration;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SwaggerConfigTest {

  @Test
  public void shouldGetDocket() {
    SwaggerConfig swaggerConfig = new SwaggerConfig();
    assertNotNull("Docket não pode ser null!", swaggerConfig.api());
  }

}