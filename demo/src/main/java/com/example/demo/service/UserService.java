package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.entities.User;
import com.example.demo.model.request.UserCreateRequest;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	private final PasswordEncoder  passEncoder;
	
	public UserService() {
		passEncoder = new BCryptPasswordEncoder();
	}

	public User CreateUser(UserCreateRequest userRequest) {

		if(GetUserByName(userRequest.getUsername()) != null)
			return null;

		User user = new User();
		user.setUsername(userRequest.getUsername());
		user.setPassword(passEncoder.encode(userRequest.getPassword()));

		userRepository.save(user);

		return user;
	}

	public User ValidateUser(String userName, String password) {

		User user = GetUserByName(userName);

		if(user == null)
			return null;

		if(passEncoder.matches(password, user.getPassword()))
			return user;
		else
			return null;

	}

	public User GetUserByName(String userName) {
		return userRepository.findByUsername(userName).orElse(null);
	}
}
