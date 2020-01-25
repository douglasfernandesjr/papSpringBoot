package com.example.project.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SiteUserRole
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SiteUserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "siteRoleId", nullable = false)
    private SiteRole siteRole;

    @Column(name = "siteUserId", nullable = false)
    private Integer siteUserId;
    
}