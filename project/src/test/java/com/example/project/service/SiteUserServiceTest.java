package com.example.project.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.example.project.domain.entities.SiteRole;
import com.example.project.domain.entities.SiteUser;
import com.example.project.domain.entities.SiteUserRole;
import com.example.project.exception.BussinessRuleException;
import com.example.project.repository.SiteRoleRepository;
import com.example.project.repository.SiteUserRepository;
import com.example.project.repository.SiteUserRoleRepository;
import com.example.project.utils.SiteRoles;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


/**
 * SiteUserServiceTest
 */
@RunWith(MockitoJUnitRunner.class)
public class SiteUserServiceTest {

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Mock
    private SiteRoleRepository siteRoleRepository;

    @Mock
    private SiteUserRoleRepository siteUserRoleRepository;

    @Mock
    private SiteUserRepository siteUserRepository;

    @InjectMocks
    private SiteUserService service;

    private static final String email = "email@email.com";
    private static final String password = "password12345";

    SiteRole admRole = SiteRole.builder().name(SiteRoles.APP_ADMIN).build();

    SiteRole userRole = SiteRole.builder().name(SiteRoles.APP_USER).build();

    SiteUser usr = SiteUser.builder().email(email).password(password).build();
    SiteUserRole usrRole1 = SiteUserRole.builder().siteUser(usr).siteRole(admRole).build();
    SiteUserRole usrRole2 = SiteUserRole.builder().siteUser(usr).siteRole(userRole).build();

    @Test
    public void when_CreateUser_thenOK(){

        //given
        when(repositoryMock.findById(anyInt())).thenReturn(Optional.of(entity));
        //then

        //when

    }

    @Test
    public void when_CreateAdminUser_thenOK(){

        //given
        when(repositoryMock.findById(anyInt())).thenReturn(Optional.of(entity));
        //then

        //when

    }

    @Test
    public void when_CreateDuplicatedUser_thenError(){
        //given
        when(siteUserRepository.findByEmail(anyString())).thenReturn(Optional.of(usr));
        //then

        service.createUser(email,password,false);
        //when
        expected.expect(BussinessRuleException.class);
        expected.expectMessage("Email já em uso");

    }

}