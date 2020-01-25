package com.example.project.domain.dto.request;

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
 * UserCreateRequestTest
 */
@RunWith(MockitoJUnitRunner.class)
public class UserCreateRequestTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_NotBeValid_WhenNull() {
        UserCreateRequest createDto = UserCreateRequest.builder().email(null).password(null).isAdmin(null).build();

        Set<ConstraintViolation<UserCreateRequest>> constraintViolations = validator.validate(createDto);

        assertTrue("Modelo deve ser inválido", constraintViolations.size() > 0);
    }

    @Test
    public void should_NotBeValid_WhenInvalidEmail() {
        UserCreateRequest createDto = UserCreateRequest.builder().email("email").password("senha12345").isAdmin(false).build();

        Set<ConstraintViolation<UserCreateRequest>> constraintViolations = validator.validate(createDto);

        assertTrue("Modelo deve ser inválido", constraintViolations.size() == 1);
    }

    @Test
    public void should_BeValid_WhenValidData() {
        UserCreateRequest createDto = UserCreateRequest.builder().email("email@email.com").password("senha12345").isAdmin(false).build();

        Set<ConstraintViolation<UserCreateRequest>> constraintViolations = validator.validate(createDto);

        assertTrue("Modelo deve ser inválido", constraintViolations.size() == 0);
    }

    @Test
    public void should_BeValid_WhenValidData_2() {
        UserCreateRequest createDto = UserCreateRequest.builder().email("email@email.com.br").password("senha12345").isAdmin(false).build();

        Set<ConstraintViolation<UserCreateRequest>> constraintViolations = validator.validate(createDto);

        assertTrue("Modelo deve ser inválido", constraintViolations.size() == 0);
    }
}