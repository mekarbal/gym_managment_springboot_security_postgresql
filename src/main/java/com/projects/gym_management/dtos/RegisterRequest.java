package com.projects.gym_management.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotEmpty(message = "Firstname cannot be Empty")
    private String firstname;
    @NotEmpty(message = "Lastname cannot be Empty")
    private String lastname;
    @NotEmpty(message = "Email cannot be Empty")
    @Email(message = "Email must be valid")
    private String email;
    @NotEmpty(message = "CIN cannot be Empty")
    private String cin;
    @NotEmpty(message = "Passsword cannot be Empty")
    @Size(min = 8 ,message = "The password must be at least 8 characters")
    private String password;
}
