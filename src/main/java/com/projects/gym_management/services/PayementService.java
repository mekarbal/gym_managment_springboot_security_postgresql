package com.projects.gym_management.services;

import com.projects.gym_management.dtos.CreatePaymentRequest;
import com.projects.gym_management.dtos.PayementResponse;
import com.projects.gym_management.dtos.PayementsListResponse;
import com.projects.gym_management.entities.Payement;
import com.projects.gym_management.entities.User;
import com.projects.gym_management.exceptions.ResourceNotFoundException;
import com.projects.gym_management.mappers.PayementMapper;
import com.projects.gym_management.mappers.UserMapper;
import com.projects.gym_management.repositories.PayementRepository;
import com.projects.gym_management.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PayementService {

    private final PayementRepository payementRepository;
    private final UserRepository userRepository;
    private final PayementMapper payementMapper;


    public PayementService(PayementRepository payementRepository, UserRepository userRepository, PayementMapper payementMapper) {
        this.payementRepository = payementRepository;
        this.userRepository = userRepository;
        this.payementMapper = payementMapper;
    }


    public PayementsListResponse getAllPayments(int pageNumber, int pageSize, Integer month, Integer year, Long userId) {
        validatePageableParameters(pageNumber, pageSize);

        PageRequest pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Payement> pageResult = payementRepository.findPayments(userId, month, year, pageable);

        return PayementMapper.toPaymentResponse(pageResult);

    }

    @Transactional
    public PayementResponse createPayment(CreatePaymentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + request.getUserId()));

        Payement payement = PayementMapper.toEntity(request, user);
        Payement savedPayement = payementRepository.save(payement);


        return PayementMapper.toResponse(savedPayement);
    }

    public PayementResponse getPayementById(Long payementId){

        Payement payement=payementRepository.findById(payementId)
                .orElseThrow(()-> new ResourceNotFoundException("Payment not found with ID: " + payementId));

        return PayementMapper.toResponse(payement);
    }
    private void validatePageableParameters(int pageNumber, int pageSize) {
        if (pageNumber <= 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Page number and page size must be greater than 0.");
        }
    }
}
