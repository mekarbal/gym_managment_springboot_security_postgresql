package com.projects.gym_management.services;

import com.projects.gym_management.dtos.CreatePaymentRequest;
import com.projects.gym_management.dtos.PayementResponse;
import com.projects.gym_management.dtos.PayementsListResponse;
import com.projects.gym_management.dtos.StatisticsResponse;
import com.projects.gym_management.entities.Payement;
import com.projects.gym_management.entities.User;
import com.projects.gym_management.exceptions.ResourceNotFoundException;
import com.projects.gym_management.mappers.PayementMapper;
import com.projects.gym_management.repositories.PayementRepository;
import com.projects.gym_management.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PayementService {

    private final PayementRepository payementRepository;
    private final UserRepository userRepository;


    public PayementService(PayementRepository payementRepository, UserRepository userRepository) {
        this.payementRepository = payementRepository;
        this.userRepository = userRepository;
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
    @Transactional
    public StatisticsResponse getStatistics(Date startDate, Date endDate) {

        Date from = startDate!=null?startDate:getStartOfYear();
        Date to =endDate!=null? endDate:getEndOfYear();
        List<Payement> payments = payementRepository.findPaymentsByDateRange(from, to);

        double totalRevenue = 0.0;
        double currentMonthRevenue = 0.0;
        Map<Integer, Float> monthlyRevenue = new HashMap<>();

        for (int i = 1; i <= 12; i++) {
            monthlyRevenue.put(i, 0.0f);
        }

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;

        for (Payement payment : payments) {
            calendar.setTime(payment.getCreatedAt());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;

            totalRevenue += payment.getAmount();

            monthlyRevenue.put(month, monthlyRevenue.get(month) + payment.getAmount().floatValue());

            if (year == currentYear && month == currentMonth) {
                currentMonthRevenue += payment.getAmount();
            }
        }

        return StatisticsResponse.builder()
                .totalRevenue(totalRevenue)
                .currentMonthRevenue(currentMonthRevenue)
                .monthlyRevenue(monthlyRevenue)
                .build();
    }

    public Date getStartOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public Date getEndOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

}
