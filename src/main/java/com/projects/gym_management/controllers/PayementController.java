package com.projects.gym_management.controllers;

import com.projects.gym_management.dtos.CreatePaymentRequest;
import com.projects.gym_management.dtos.PayementResponse;
import com.projects.gym_management.dtos.PayementsListResponse;
import com.projects.gym_management.dtos.StatisticsResponse;
import com.projects.gym_management.services.PayementService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/payments")
public class PayementController {

    private final PayementService payementService;

    public PayementController(PayementService payementService) {
        this.payementService = payementService;
    }

    @GetMapping
    public PayementsListResponse getPayments(
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Long userId) {

        return payementService.getAllPayments(pageNumber, pageSize, month, year, userId);
    }

    @PostMapping
    public ResponseEntity<PayementResponse> createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        PayementResponse paymentResponse = payementService.createPayment(request);
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{payementId}")
    public ResponseEntity<PayementResponse> getPayementById(@PathVariable Long payementId){
        PayementResponse payementResponse=payementService.getPayementById(payementId);
        return new ResponseEntity<>(payementResponse,HttpStatus.ACCEPTED);
    }

    @GetMapping("/revenue-statistics")
    public StatisticsResponse getRevenueStatistics(
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,

            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        return payementService.getStatistics(startDate, endDate);
    }


}
