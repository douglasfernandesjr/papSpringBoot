package com.example.project.domain.dto.request;

import javax.validation.constraints.NotEmpty;

import com.example.project.domain.validators.Phone;

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
    @Phone(message = "phone is invalid")
    private String phone;
}