package com.projects.gym_management.dtos;

import com.projects.gym_management.entities.Payement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class PayementsListResponse {
    private String message;
    private List<PayementResponse> payments;
    private int totalPages;
    private int pageNumber;
    private int pageSize;
}
