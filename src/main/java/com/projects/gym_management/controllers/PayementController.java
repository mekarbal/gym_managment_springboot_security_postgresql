package com.projects.gym_management.controllers;

import com.projects.gym_management.dtos.CreatePaymentRequest;
import com.projects.gym_management.dtos.PayementResponse;
import com.projects.gym_management.dtos.PayementsListResponse;
import com.projects.gym_management.services.PayementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<PayementResponse> createPayment(@RequestBody CreatePaymentRequest request) {
        PayementResponse paymentResponse = payementService.createPayment(request);
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{payementId}")
    public ResponseEntity<PayementResponse> getPayementById(@PathVariable Long payementId){
        PayementResponse payementResponse=payementService.getPayementById(payementId);
        return new ResponseEntity<>(payementResponse,HttpStatus.ACCEPTED);
    }

}
