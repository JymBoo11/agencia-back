package com.viajes.payments.repository;

import com.viajes.payments.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByTripId(Long tripId);
    List<Payment> findByUserId(Long userId);
    java.util.Optional<Payment> findByStripePaymentIntentId(String stripePaymentIntentId);
}