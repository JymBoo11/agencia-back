package com.viajes.booking.dto;

public record BookingRequest(
    Long tripId,
    Long userId,
    com.viajes.booking.model.Booking.BookingType type,
    String detailsJson
) {}