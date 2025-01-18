package com.projects.gym_management.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotEmpty(message = "Email cannot be Empty")
    @Email(message = "Email must be valid")
    private String email;
    @NotEmpty(message = "Passsword cannot be Empty")
    String password;
}