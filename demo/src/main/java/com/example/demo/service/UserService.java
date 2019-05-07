package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.entities.Client;
import com.example.demo.model.entities.User;
import com.example.demo.model.request.UserCreateRequest;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	
	private final UserRepository userRepository;
	private final PasswordEncoder  passEncoder;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
		passEncoder = new BCryptPasswordEncoder();
	}
	
	public User CreateUser(UserCreateRequest userRequest) {
		User user = new User();
		user.setUsername(userRequest.getUsername());
		user.setPassword(passEncoder.encode(userRequest.getPassword()));

		userRepository.save(user);
		
		return user;
	}
	
	public User ValidateUser(String user, String password) {
		
		User user = new User();
		user.setUsername(userRequest.getUsername());
		user.setPassword(passEncoder.encode(userRequest.getPassword()));

		userRepository.save(user);
		
		userRepository.
		
		return user;
		
	}
}
