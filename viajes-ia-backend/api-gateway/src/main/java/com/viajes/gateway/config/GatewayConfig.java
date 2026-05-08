package com.viajes.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("user-service", r -> r.path("/api/auth/**", "/api/users/**")
                .uri("http://localhost:8081"))
            .route("ai-service", r -> r.path("/api/ai/**")
                .uri("http://localhost:8083"))
            .route("trip-service", r -> r.path("/api/trips/**")
                .uri("http://localhost:8082"))
            .route("payment-service", r -> r.path("/api/payments/**")
                .uri("http://localhost:8084"))
            .route("booking-service", r -> r.path("/api/bookings/**")
                .uri("http://localhost:8085"))
            .build();
    }
}