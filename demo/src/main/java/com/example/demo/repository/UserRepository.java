package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.entities.Client;
import com.example.demo.model.entities.User;

public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {
} 
