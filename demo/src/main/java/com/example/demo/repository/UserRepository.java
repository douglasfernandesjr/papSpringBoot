package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.entities.User;

public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {
	
	Optional<User> findByUsername(String userName);
} 
