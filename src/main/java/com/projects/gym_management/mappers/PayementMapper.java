package com.projects.gym_management.mappers;

import com.projects.gym_management.dtos.CreatePaymentRequest;
import com.projects.gym_management.dtos.PayementResponse;
import com.projects.gym_management.dtos.PayementsListResponse;
import com.projects.gym_management.entities.Payement;
import com.projects.gym_management.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class PayementMapper {


    public static PayementsListResponse toPaymentResponse(Page<Payement> pageResult) {

        List<PayementResponse> payementResponses=pageResult.getContent().stream().map(payement -> {
            UserMapper userMapper = new UserMapper();
            User user = payement.getUser();
            return PayementResponse.builder()
                    .id(payement.getId())
                    .user(userMapper.toResponse(user))
                    .amount(payement.getAmount())
                    .createdAt(payement.getCreatedAt())
                    .expireAt(payement.getExpireAt()).build();

        }).toList();


        return PayementsListResponse.builder()
                .message("Payments retrieved successfully")
                .payments(payementResponses)
                .totalPages(pageResult.getTotalPages())
                .pageNumber(pageResult.getNumber())
                .pageSize(pageResult.getSize())
                .build();
    }

    public static Payement toEntity(CreatePaymentRequest request, User user) {
        Payement payement = new Payement();
        payement.setUser(user);
        payement.setAmount(request.getAmount());
        payement.setExpireAt(request.getExpireAt());
        payement.setCreatedAt(new Date());
        return payement;
    }
    public static PayementResponse toResponse(Payement payement) {
        UserMapper userMapper = new UserMapper();
        return PayementResponse.builder()
                .id(payement.getId())
                .user(userMapper.toResponse(payement.getUser()))
                .amount(payement.getAmount())
                .expireAt(payement.getExpireAt())
                .createdAt(payement.getCreatedAt())
                .build();
    }
}
