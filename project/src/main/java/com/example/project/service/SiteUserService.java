package com.example.project.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.example.project.domain.entities.SiteRole;
import com.example.project.domain.entities.SiteUser;
import com.example.project.domain.entities.SiteUserRole;
import com.example.project.exception.BussinessRuleException;
import com.example.project.repository.SiteRoleRepository;
import com.example.project.repository.SiteUserRepository;
import com.example.project.repository.SiteUserRoleRepository;
import com.example.project.utils.SiteRoles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * SiteUserService
 */
@Service
public class SiteUserService {

	private final SiteUserRoleRepository siteUserRoleRepository;

	private final SiteUserRepository siteUserRepository;

	private final SiteRoleRepository siteRoleRepository;

	private final PasswordEncoder passEncoder;

	@Autowired
	public SiteUserService(SiteUserRepository siteUserRepository, //
			SiteUserRoleRepository siteUserRoleRepository, //
			SiteRoleRepository siteRoleRepository) {
		this.siteUserRoleRepository = siteUserRoleRepository;
		this.siteUserRepository = siteUserRepository;
		this.siteRoleRepository = siteRoleRepository;
		this.passEncoder = new BCryptPasswordEncoder();
	}

	public SiteUser createUser(String email, String password, boolean isAdmin) {
		Optional<SiteUser> usr = siteUserRepository.findByEmail(email);

		if (usr.isPresent()) {
			throw new BussinessRuleException("Email já em uso");
		}

		SiteUser newUser = SiteUser.builder().email(email).password(passEncoder.encode(password)).build();

		siteUserRepository.save(newUser);

		Set<SiteUserRole> roles = new HashSet<SiteUserRole>();

		roles.add(getUserRole(newUser, SiteRoles.APP_USER));
		if (isAdmin) {
			roles.add(getUserRole(newUser, SiteRoles.APP_ADMIN));
		}

		siteUserRoleRepository.saveAll(roles);

		newUser.setRoles(roles);

		return newUser;
	}

	public SiteRole getRole(String role) {
		Optional<SiteRole> siteRole = siteRoleRepository.findById(role);

		if (siteRole.isPresent())
			return siteRole.get();
		else
			return siteRoleRepository.save(new SiteRole(role));
	}

	private SiteUserRole getUserRole(SiteUser usr, String role) {
		SiteRole siteRole = getRole(role);
		return SiteUserRole.builder().siteUserId(usr.getId()).siteRole(siteRole).build();
	}

	public SiteUser ValidateUser(String userName, String password) {

		Optional<SiteUser> user = siteUserRepository.findByEmail(userName);

		if (!user.isPresent())
			return null;

		if (passEncoder.matches(password, user.get().getPassword()))
			return user.get();
		else
			return null;

	}

}