package com.service.hydrometrics.models.DTO.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequestDTO implements Serializable {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}