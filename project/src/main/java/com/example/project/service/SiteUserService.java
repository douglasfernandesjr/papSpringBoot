package com.example.project.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.example.project.domain.entities.SiteRole;
import com.example.project.domain.entities.SiteUser;
import com.example.project.domain.entities.SiteUserRole;
import com.example.project.exception.BussinessRuleException;
import com.example.project.repository.SiteUserRepository;
import com.example.project.repository.SiteUserRoleRepository;
import com.example.project.utils.SiteRoles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SiteUserService
 */
@Service
public class SiteUserService {

	private final SiteUserRoleRepository siteUserRoleRepository;

	private final SiteUserRepository siteUserRepository;

	@Autowired
	public SiteUserService(SiteUserRepository siteUserRepository, SiteUserRoleRepository siteUserRoleRepository) {
		this.siteUserRoleRepository = siteUserRoleRepository;
		this.siteUserRepository = siteUserRepository;
	}

	public SiteUser createUser(String email, String password, boolean isAdmin) {
		Optional<SiteUser> usr = siteUserRepository.findByEmail(email);

		if (usr.isPresent()) {
			throw new BussinessRuleException("Email já em uso");
		}

		SiteUser newUser = SiteUser.builder().email(email).password(password).build();

		siteUserRepository.save(newUser);

		Set<SiteUserRole> roles = new HashSet<SiteUserRole>();

		roles.add(SiteUserRole.builder().siteUser(newUser).siteRole(new SiteRole(SiteRoles.APP_USER)).build());
		if (isAdmin) {
			roles.add(SiteUserRole.builder().siteUser(newUser).siteRole(new SiteRole(SiteRoles.APP_ADMIN)).build());
		}

		siteUserRoleRepository.saveAll(roles);

		newUser.setRoles(roles);

		return newUser;
	}

}