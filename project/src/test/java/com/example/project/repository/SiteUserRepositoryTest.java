package com.example.project.repository;

import org.junit.Before;
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

import java.util.Optional;

import com.example.project.domain.entities.SiteRole;
import com.example.project.domain.entities.SiteUser;
import com.example.project.domain.entities.SiteUserRole;
import com.example.project.utils.IntegrationTestConfig;
import com.example.project.utils.SiteRoles;

/**
 * SiteUserRepositoryTest
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = IntegrationTestConfig.appProperties)
@ActiveProfiles("test")
public class SiteUserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SiteUserRepository siteUserRepository;

    private static final String email = "email@email.com";
    private static final String password = "password12345";

    @Before
    public void setUp() {
        // given

        entityManager.clear();

        SiteRole admRole = SiteRole.builder().name(SiteRoles.APP_ADMIN).build();
        entityManager.persist(admRole);

        SiteRole userRole = SiteRole.builder().name(SiteRoles.APP_USER).build();
        entityManager.persist(userRole);

        SiteUser usr = SiteUser.builder().email(email).password(password).build();
        entityManager.persist(usr);

        SiteUserRole sUsrRole1 = SiteUserRole.builder().siteUserId(usr.getId()).siteRole(admRole).build();
        SiteUserRole sUsrRole2 = SiteUserRole.builder().siteUserId(usr.getId()).siteRole(admRole).build();

        
        entityManager.persist(sUsrRole1);
        entityManager.persist(sUsrRole2);
        entityManager.flush();

        entityManager.refresh(usr);


    }

    @Test
    public void when_FindByEmailAndPassword_withValue() {
        // when
        Optional<SiteUser> found = siteUserRepository.findByEmailAndPassword(email, password);
        // then
        assertNotNull(found);
        assertEquals(found.isPresent(), true);
        assertEquals(found.get().getEmail(), email);
        assertEquals(found.get().getPassword(), password);

        assertEquals(found.get().getRoles().size(), 2);
        assertNotNull(found.get().getRoles().iterator().next().getSiteRole());
    }

    @Test
    public void when_FindByEmail_withValue() {
        // when
        Optional<SiteUser> found = siteUserRepository.findByEmail(email);
        // then
        assertNotNull(found);
        assertEquals(found.isPresent(), true);
        assertEquals(found.get().getEmail(), email);
        assertEquals(found.get().getPassword(), password);

        assertEquals(found.get().getRoles().size(), 2);
        assertNotNull(found.get().getRoles().iterator().next().getSiteRole());
    }

}