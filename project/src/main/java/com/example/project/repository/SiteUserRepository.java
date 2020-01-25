package com.example.project.repository;

import java.util.Optional;

import com.example.project.domain.entities.SiteUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SiteUserRepository
 */
@Repository
public interface SiteUserRepository extends JpaRepository<SiteUser, Integer> {

    Optional<SiteUser> findByEmail(String email);

    Optional<SiteUser> findByEmailAndPassword(String email, String password);
}
