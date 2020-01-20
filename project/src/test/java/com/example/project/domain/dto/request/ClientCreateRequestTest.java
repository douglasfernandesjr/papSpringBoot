package com.example.project.domain.dto.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * ClientCreateRequestTest
 */
@RunWith(MockitoJUnitRunner.class)
public class ClientCreateRequestTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_NotBeValid_WhenPhoneInvalid() {
        ClientCreateRequest createDto = ClientCreateRequest.builder().name("name").phone("invalido").build();

        Set<ConstraintViolation<ClientCreateRequest>> constraintViolations = validator.validate(createDto);

        assertTrue("Modelo deve ser inválido", constraintViolations.size() > 0);
    }

    @Test
    public void should_NotBeValid_WhenNameInvalid() {
        ClientCreateRequest createDto = ClientCreateRequest.builder().name(null).phone("987654321").build();

        Set<ConstraintViolation<ClientCreateRequest>> constraintViolations = validator.validate(createDto);

        assertTrue("Modelo deve ser inválido", constraintViolations.size() > 0);
    }


    @Test
    public void should_BeValid() {
        ClientCreateRequest createDto = ClientCreateRequest.builder().name("name").phone("987654321").build();

        Set<ConstraintViolation<ClientCreateRequest>> constraintViolations = validator.validate(createDto);

        assertEquals("Modelo deve ser válido", constraintViolations.size(), 0);
    }

}