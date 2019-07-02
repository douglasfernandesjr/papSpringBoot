package com.example.demo.unit.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Optional;

import com.example.demo.model.entities.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserServiceUnitTest {

    @TestConfiguration
    static class UserServiceTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserService();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private final String alexUserName  = "Alex";
    private final String alexPassword  = "123456";

    @Before
    public void setUp() {

        PasswordEncoder passEncoder = new BCryptPasswordEncoder();

        /* Mock */
        User alex = new User();
        alex.setUsername(alexUserName);
        alex.setPassword(passEncoder.encode(alexPassword));

        Mockito.when(userRepository.findByUsername(alexUserName)).thenReturn(Optional.of(alex));
        /* Mock */
    }

    @Test
    public void shouldReturnNull() {
        User user = userService.GetUserByName("NotFound");
        assertNull(user);
    }

    @Test
    public void shouldReturnValidUser() {
        User user = userService.GetUserByName(alexUserName);
        assertNotNull(user);
    }

    @Test
    public void shouldValidatePassword() {
        User user = userService.ValidateUser(alexUserName, alexPassword);
        assertNotNull(user);
    }

    @Test
    public void shouldInvalidatePassword() {
        User user = userService.ValidateUser(alexUserName, alexPassword+"wrongpassword");
        assertNull(user);
    }

}