package com.service.hydrometrics.models.DTO.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class CreateUserDTO implements Serializable {
    @NotEmpty
    private final String username;
    @NotEmpty
    private final String password;
    @NotEmpty
    private final String firstName;
    @NotEmpty
    private final String lastName;
    @Email
    private final String email;
    @NotEmpty
    private final String role;
    private final byte enabled;
}