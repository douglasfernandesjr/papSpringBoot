package com.example.project.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.project.domain.entities.SiteRole;
import com.example.project.domain.entities.SiteUser;
import com.example.project.domain.entities.SiteUserRole;
import com.example.project.exception.BussinessRuleException;
import com.example.project.repository.SiteRoleRepository;
import com.example.project.repository.SiteUserRepository;
import com.example.project.repository.SiteUserRoleRepository;
import com.example.project.utils.SiteRoles;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SiteUserServiceTest
 */
@RunWith(MockitoJUnitRunner.class)
public class SiteUserServiceTest {

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Mock
    private SiteUserRoleRepository siteUserRoleRepository;

    @Mock
    private SiteUserRepository siteUserRepository;

    @Mock
    private SiteRoleRepository siteRoleRepository;

    @Spy
    private PasswordEncoder passEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    private SiteUserService service;

    private static final String email = "email@email.com";
    private static final String password = "password12345";

    SiteRole admRole = SiteRole.builder().name(SiteRoles.APP_ADMIN).build();

    SiteRole userRole = SiteRole.builder().name(SiteRoles.APP_USER).build();

    SiteUser usr = SiteUser.builder().id(1).email(email).password(password).build();
    SiteUserRole usrRole1 = SiteUserRole.builder().siteUserId(usr.getId()).siteRole(admRole).build();
    SiteUserRole usrRole2 = SiteUserRole.builder().siteUserId(usr.getId()).siteRole(userRole).build();

    Optional<SiteUser> optional = Optional.ofNullable(null);

    @Test
    public void when_CreateUser_thenOK() {

        // given
        when(siteRoleRepository.findById(SiteRoles.APP_USER)).thenReturn(Optional.of(userRole));
        when(siteUserRepository.findByEmail(anyString())).thenReturn(optional);
        when(siteUserRepository.save(any())).then(returnsFirstArg());
        when(siteUserRoleRepository.saveAll(any())).thenAnswer(i -> {
            @SuppressWarnings("unchecked")
            Iterable<SiteUserRole> entities = (Iterable<SiteUserRole>) i.getArguments()[0];
            List<SiteUserRole> result = new ArrayList<SiteUserRole>();
            entities.forEach(result::add);
            return result;
        });

        // then
        SiteUser newUser = service.createUser(email, password, false);
        // when

        assertNotNull(newUser);
        assertEquals(newUser.getEmail(), email);
        assertEquals(newUser.getPassword(), password);

        assertEquals(newUser.getRoles().size(), 1);
        assertNotNull(newUser.getRoles().iterator().next().getSiteRole());

    }

    @Test
    public void when_CreateAdminUser_thenOK() {

        // given
        when(siteRoleRepository.findById(SiteRoles.APP_ADMIN)).thenReturn(Optional.of(admRole));
        when(siteRoleRepository.findById(SiteRoles.APP_USER)).thenReturn(Optional.of(userRole));
        when(siteUserRepository.findByEmail(anyString())).thenReturn(optional);
        when(siteUserRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(siteUserRoleRepository.saveAll(any())).thenAnswer(i -> {
            @SuppressWarnings("unchecked")
            Iterable<SiteUserRole> entities = (Iterable<SiteUserRole>) i.getArguments()[0];
            List<SiteUserRole> result = new ArrayList<SiteUserRole>();
            entities.forEach(result::add);
            return result;
        });

        // then
        SiteUser newUser = service.createUser(email, password, true);

        // when
        assertNotNull(newUser);
        assertEquals(newUser.getEmail(), email);
        assertEquals(newUser.getPassword(), password);

        assertEquals(newUser.getRoles().size(), 2);
        assertNotNull(newUser.getRoles().iterator().next().getSiteRole());

    }

    @Test
    public void when_CreateDuplicatedUser_thenError() {
        // given
        when(siteUserRepository.findByEmail(anyString())).thenReturn(Optional.of(usr));
        // then
        expected.expect(BussinessRuleException.class);
        expected.expectMessage("Email já em uso");

        service.createUser(email, password, false);
        // when

    }

    @Test
    public void when_GetEmptyRole_thenCreate() {
        // given
        when(siteRoleRepository.findById(anyString())).thenReturn(Optional.ofNullable(null));
        when(siteRoleRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        // when
        SiteRole role = service.getRole(SiteRoles.APP_ADMIN);

        // then
        assertNotNull(role);
        assertEquals(role.getName(), SiteRoles.APP_ADMIN);

    }

    @Test
    public void when_GetRole_thenReturn() {
        // given
        when(siteRoleRepository.findById(anyString())).thenReturn(Optional.of(admRole));
        // when
        SiteRole role = service.getRole(SiteRoles.APP_ADMIN);

        // then
        assertNotNull(role);
        assertEquals(role.getName(), SiteRoles.APP_ADMIN);
    }

}