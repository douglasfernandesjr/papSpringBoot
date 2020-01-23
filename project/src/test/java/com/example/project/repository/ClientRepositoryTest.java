package com.example.project.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import com.example.project.domain.entities.Client;
import com.example.project.utils.IntegrationTestConfig;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = IntegrationTestConfig.appProperties)
@ActiveProfiles("test")
public class ClientRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void when_ListDistinct_OK() {
        // given
        entityManager.persist(Client.builder().name("nome").phone("11967321234").build());
        entityManager.persist(Client.builder().name("nome").phone("11977321234").build());
        entityManager.persist(Client.builder().name("nomeDiferente").phone("11677321234").build());
        entityManager.flush();
        // when
        List<String> found = clientRepository.listDistinct();
        // then
        assertNotNull(found);
        assertEquals(found.size(), 2);
    }

}