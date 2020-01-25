package com.example.project.repository;

import com.example.project.domain.entities.SiteUserRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SiteUserRepository
 */
@Repository
public interface SiteUserRoleRepository extends JpaRepository<SiteUserRole, Integer> {

}
