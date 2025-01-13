package com.projects.gym_management.dtos;

import lombok.Data;

@Data
public class UpdateRoleRequest {
    private String email;
    private String role;
}
