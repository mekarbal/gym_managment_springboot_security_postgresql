package com.projects.gym_management.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class CreatePaymentRequest {
    @NotNull(message = "User ID cannot be null")
    private Integer userId;

    @NotNull(message = "Amount cannot be null")
    private Double amount;

    private Date expireAt;
}
