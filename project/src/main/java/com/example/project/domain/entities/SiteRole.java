package com.example.project.domain.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;

/**
 * SiteRole
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SiteRole implements GrantedAuthority {

    /**
     *
     */
    private static final long serialVersionUID = 6885827715198494513L;
    
    @Id
    private String name;

    @Override
    public String getAuthority() {
        return this.getName();
    }
}