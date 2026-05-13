package com.viajes.payments.controller;

import com.viajes.payments.model.Payment;
import com.viajes.payments.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(
            @RequestParam Long tripId,
            @RequestParam Long userId,
            @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(paymentService.createPayment(tripId, userId, amount));
    }

    @PostMapping("/confirm")
    public ResponseEntity<Payment> confirmPayment(@RequestParam String paymentIntentId) {
        return ResponseEntity.ok(paymentService.confirmPayment(paymentIntentId));
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<Payment>> getPaymentsByTrip(@PathVariable Long tripId) {
        return ResponseEntity.ok(paymentService.getPaymentsByTripId(tripId));
    }
}
