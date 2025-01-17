package com.projects.gym_management.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projects.gym_management.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String cin;
    private Role role;
}