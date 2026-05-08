package com.viajes.booking.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tripId;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private BookingType type;

    private String providerReference;
    private String status; // CONFIRMED, PENDING, CANCELLED

    @Column(columnDefinition = "TEXT")
    private String detailsJson;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public enum BookingType {
        FLIGHT, HOTEL, CAR, ACTIVITY, INSURANCE, TRANSFER
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTripId() { return tripId; }
    public void setTripId(Long tripId) { this.tripId = tripId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public BookingType getType() { return type; }
    public void setType(BookingType type) { this.type = type; }

    public String getProviderReference() { return providerReference; }
    public void setProviderReference(String providerReference) { this.providerReference = providerReference; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDetailsJson() { return detailsJson; }
    public void setDetailsJson(String detailsJson) { this.detailsJson = detailsJson; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}