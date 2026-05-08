package com.viajes.payments.service;

import com.viajes.payments.model.Payment;
import com.viajes.payments.repository.PaymentRepository;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final StripeService stripeService;

    public Payment createPayment(Long tripId, Long userId, BigDecimal amount) {
        Payment payment = new Payment();
        payment.setTripId(tripId);
        payment.setUserId(userId);
        payment.setAmount(amount);
        payment.setStatus(Payment.PaymentStatus.PENDING);

        try {
            com.stripe.model.PaymentIntent intent = stripeService.createPaymentIntent(amount, "EUR", tripId);
            payment.setStripePaymentIntentId(intent.getId());
        } catch (StripeException e) {
            throw new RuntimeException("Error creating Stripe payment intent", e);
        }

        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment confirmPayment(String paymentIntentId) {
        Payment payment = paymentRepository.findByStripePaymentIntentId(paymentIntentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        try {
            com.stripe.model.PaymentIntent intent = stripeService.confirmPayment(paymentIntentId);
            if ("succeeded".equals(intent.getStatus())) {
                payment.setStatus(Payment.PaymentStatus.COMPLETED);
            } else {
                payment.setStatus(Payment.PaymentStatus.FAILED);
            }
        } catch (StripeException e) {
            payment.setStatus(Payment.PaymentStatus.FAILED);
        }

        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsByTripId(Long tripId) {
        return paymentRepository.findByTripId(tripId);
    }
}