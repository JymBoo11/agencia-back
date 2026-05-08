package com.viajes.booking.service;

import com.viajes.booking.dto.BookingRequest;

public class ActivityBookingService {
    public static String bookActivity(BookingRequest request) {
        return "ACTIVITY_" + System.currentTimeMillis();
    }
}