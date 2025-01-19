package com.projects.gym_management.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginatedUserResponse {
    private List<UserResponse> data;
    private int page;
    private int perPage;
    private long count;
    private int totalPages;
}
