package com.viajes.booking.service;

import com.viajes.booking.dto.BookingRequest;

public class CarBookingService {
    public static String bookCar(BookingRequest request) {
        return "CAR_" + System.currentTimeMillis();
    }
}