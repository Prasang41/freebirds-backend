package com.FreeBirds.FreeBirds.repository;

import com.FreeBirds.FreeBirds.entities.Contract;
import com.FreeBirds.FreeBirds.entities.Payment;
import com.FreeBirds.FreeBirds.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByContract(Contract contract);
    List<Payment> findByStatus(PaymentStatus status);
}

