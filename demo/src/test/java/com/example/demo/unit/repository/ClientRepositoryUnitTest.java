package com.example.demo.unit.repository;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;

import java.util.List;

import com.example.demo.model.entities.Client;
import com.example.demo.model.entities.User;
import com.example.demo.model.projections.ClientLoginProjection;
import com.example.demo.repository.ClientRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientRepositoryUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

    private final String alexUserName  = "Alex";
    private final String alexPassword  = "123456";


    @Before
    public void setUp() {

        PasswordEncoder passEncoder = new BCryptPasswordEncoder();

        /* Mock */
        User alex = new User();
        alex.setUsername(alexUserName);
        alex.setPassword(passEncoder.encode(alexPassword));

        entityManager.persist(alex);
        entityManager.flush();

      
        
        /* Mock */
    }


    @Test
    public void whenListClientLogin_thenReturnHasValue() {
        Client alexC = new Client();
        alexC.setName(alexUserName);
        alexC.setPhone("11967321234");


        entityManager.persist(alexC);
        entityManager.flush();
        // when
        List<ClientLoginProjection> found = clientRepository.ListClientLogin();
        // then
        assertNotNull(found);
        assertThat(found.size(), is(1));
    }

    @Test
    public void whenListClientLogin_thenReturnEmpty() {
     
        // when
        List<ClientLoginProjection> found = clientRepository.ListClientLogin();
        // then
        assertNotNull(found);
        assertThat(found.size(), is(0));
    }

}