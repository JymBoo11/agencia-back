package com.viajes.booking.service;

import com.viajes.booking.dto.BookingRequest;

public class FlightBookingService {
    public static String bookFlight(BookingRequest request) {
        return "FLIGHT_" + System.currentTimeMillis();
    }
}