package com.example.project.domain.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SiteRole
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SiteRole {

    @Id
    private String name;
}