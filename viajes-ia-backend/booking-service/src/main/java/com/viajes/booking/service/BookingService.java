package com.viajes.booking.service;

import com.viajes.booking.model.Booking;
import com.viajes.booking.repository.BookingRepository;
import com.viajes.booking.dto.BookingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public List<Booking> getBookingsByTripId(Long tripId) {
        return bookingRepository.findByTripId(tripId);
    }

    public Booking createBooking(BookingRequest request) {
        Booking booking = new Booking();
        booking.setTripId(request.tripId());
        booking.setUserId(request.userId());
        booking.setType(request.type());
        booking.setStatus("PENDING");

        booking = bookingRepository.save(booking);

        String reference = switch (request.type()) {
            case FLIGHT -> FlightBookingService.bookFlight(request);
            case HOTEL -> HotelBookingService.bookHotel(request);
            case CAR -> CarBookingService.bookCar(request);
            case ACTIVITY -> ActivityBookingService.bookActivity(request);
            default -> "MANUAL_" + booking.getId();
        };

        booking.setProviderReference(reference);
        booking.setStatus("CONFIRMED");
        return bookingRepository.save(booking);
    }
}