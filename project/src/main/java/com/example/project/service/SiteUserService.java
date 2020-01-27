package com.example.project.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.project.domain.entities.SiteRole;
import com.example.project.domain.entities.SiteUser;
import com.example.project.domain.entities.SiteUserRole;
import com.example.project.exception.BussinessRuleException;
import com.example.project.repository.SiteRoleRepository;
import com.example.project.repository.SiteUserRepository;
import com.example.project.repository.SiteUserRoleRepository;
import com.example.project.utils.SiteRoles;

import org.springframework.beans.factory.annotation.Autowired;
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
			SiteRoleRepository siteRoleRepository,PasswordEncoder passEncoder) {
		this.siteUserRoleRepository = siteUserRoleRepository;
		this.siteUserRepository = siteUserRepository;
		this.siteRoleRepository = siteRoleRepository;
		this.passEncoder = passEncoder;
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

	public List<SiteRole> rolesFrom(String usrName) {
		Optional<SiteUser> user = siteUserRepository.findByEmail(usrName);

		if (!user.isPresent())
			return new ArrayList<SiteRole>();
		else
			return rolesFrom(user.get());
	}

	public List<SiteRole> rolesFrom(SiteUser usr) {
		return usr.getRoles().stream() //
				.map(x -> x.getSiteRole()).collect(Collectors.toList());
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