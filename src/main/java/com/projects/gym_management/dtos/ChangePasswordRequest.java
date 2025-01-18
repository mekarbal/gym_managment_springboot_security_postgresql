package com.projects.gym_management.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {

    @NotEmpty(message = "Current password cannot be empty")
    private String currentPassword;

    @NotEmpty(message = "New password cannot be empty")
    @Size(min = 8, message = "The new password must be at least 8 characters")
    private String newPassword;

    @NotEmpty(message = "Confirmation password cannot be empty")
    @Size(min = 8, message = "The confirmation password must be at least 8 characters")
    private String confirmationPassword;



}


