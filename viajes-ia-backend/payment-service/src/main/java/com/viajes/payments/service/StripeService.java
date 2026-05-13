package com.viajes.payments.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripeService {

    @Value("${stripe.api.key:sk_test_placeholder}")
    private String stripeApiKey;

    public PaymentIntent createPaymentIntent(BigDecimal amount, String currency, Long tripId) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount.multiply(BigDecimal.valueOf(100)).longValue())
                .setCurrency(currency)
                .putMetadata("tripId", tripId.toString())
                .build();

        return PaymentIntent.create(params);
    }

    public PaymentIntent confirmPayment(String paymentIntentId) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);
        return intent.confirm();
    }
}
