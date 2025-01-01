package com.projects.gym_management.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PayementResponse {
    private Long id;
    private UserResponse user;
    private Double amount;
    private Date expireAt;
    private Date createdAt;
}
