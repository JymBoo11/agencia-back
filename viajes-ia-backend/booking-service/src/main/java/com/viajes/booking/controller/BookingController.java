package com.viajes.booking.controller;

import com.viajes.booking.dto.BookingRequest;
import com.viajes.booking.model.Booking;
import com.viajes.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<Booking>> getBookingsByTrip(@PathVariable Long tripId) {
        return ResponseEntity.ok(bookingService.getBookingsByTripId(tripId));
    }
}