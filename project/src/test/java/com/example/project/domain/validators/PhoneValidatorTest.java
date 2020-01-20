package com.example.project.domain.validators;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.AssertFalse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * PhoneValidatorTest
 */
@RunWith(MockitoJUnitRunner.class)
public class PhoneValidatorTest {

    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    PhoneValidator phoneValidator;

    @Before
    public void setUp() {

        phoneValidator = new PhoneValidator();

    }

    @Test
    public void should_NotBeValid_WhenNotNumber() {
        assertFalse(phoneValidator.isValid("String phone", constraintValidatorContext));
    }
    @Test
    public void should_NotBeValid_When7digits() {
        assertFalse(phoneValidator.isValid("7654321", constraintValidatorContext));
    }

    @Test
    public void should_BeValid_When9digits() {
        assertTrue(phoneValidator.isValid("987654321", constraintValidatorContext));
    }

    @Test
    public void should_BeValid_When11digits() {
        assertTrue(phoneValidator.isValid("11987654321", constraintValidatorContext));
    }

}