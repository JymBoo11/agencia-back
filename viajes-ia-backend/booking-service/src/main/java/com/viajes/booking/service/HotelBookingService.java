package com.viajes.booking.service;

import com.viajes.booking.dto.BookingRequest;

public class HotelBookingService {
    public static String bookHotel(BookingRequest request) {
        return "HOTEL_" + System.currentTimeMillis();
    }
}