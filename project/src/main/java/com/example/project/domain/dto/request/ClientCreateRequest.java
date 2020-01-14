package com.example.project.domain.dto.request;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientCreateRequest {

    @NotEmpty(message = "name is required")
    private String name;

    @NotEmpty(message = "phone is required")
    private String phone;
}