package com.projects.gym_management.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRoleRequest {

    @NotEmpty(message = "Email cannot be Empty")
    @Email(message = "Email must be valid")
    private String email;

    @NotEmpty(message = "Role cannot be Empty")
    @NotNull(message = "Email cannot be Null")
    private String role;
}
