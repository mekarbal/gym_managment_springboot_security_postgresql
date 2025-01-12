package com.projects.gym_management.dtos;


import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class StatisticsResponse {
    private double totalRevenue;
    private double currentMonthRevenue;
    private Map<Integer, Float> monthlyRevenue;
}
