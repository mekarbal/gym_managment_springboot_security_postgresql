package com.projects.gym_management.repositories;

import com.projects.gym_management.entities.Payement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PayementRepository extends JpaRepository<Payement,Long> {

    @Query("SELECT p FROM Payement p WHERE " +
            "(p.user.id = :userId OR :userId IS NULL) AND " +
            "(EXTRACT(MONTH FROM p.createdAt) = :month OR :month IS NULL) AND " +
            "(EXTRACT(YEAR FROM p.createdAt) = :year OR :year IS NULL)")
    Page<Payement> findPayments(@Param("userId") Long userId,
                                @Param("month") Integer month,
                                @Param("year") Integer year,
                                Pageable pageable);

    @Query("SELECT p FROM Payement p WHERE p.createdAt BETWEEN :startDate AND :endDate")
    List<Payement> findPaymentsByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    ;

}
