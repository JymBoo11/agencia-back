package com.viajes.trips.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viajes.trips.model.Trip;
import com.viajes.trips.repository.TripRepository;
import com.viajes.trips.dto.CreateTripRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Trip createTripFromRecommendation(Long userId, CreateTripRequest request) {
        Trip trip = new Trip();
        trip.setUserId(userId);
        trip.setTitle("Viaje a " + request.destination());
        trip.setStatus(Trip.TripStatus.DRAFT);

        try {
            String itineraryJson = objectMapper.writeValueAsString(request.itinerary());
            trip.setItineraryJson(itineraryJson);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing itinerary", e);
        }

        trip.setTotalPrice(BigDecimal.valueOf(request.totalPrice()));
        trip.setPriceBreakdownJson(request.priceBreakdownJson());

        return tripRepository.save(trip);
    }

    public Trip getTripById(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
    }

    public List<Trip> getTripsByUserId(Long userId) {
        return tripRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Trip updateTripStatus(Long tripId, Trip.TripStatus status) {
        Trip trip = getTripById(tripId);
        trip.setStatus(status);
        return tripRepository.save(trip);
    }

    public byte[] generatePdfBudget(Long tripId) {
        Trip trip = getTripById(tripId);
        return PdfGenerator.generateBudgetPdf(trip);
    }
}
