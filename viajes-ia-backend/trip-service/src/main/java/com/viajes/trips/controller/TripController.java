package com.viajes.trips.controller;

import com.viajes.trips.dto.CreateTripRequest;
import com.viajes.trips.model.Trip;
import com.viajes.trips.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping
    public ResponseEntity<Trip> createTrip(@RequestBody CreateTripRequest request,
                                           @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(tripService.createTripFromRecommendation(userId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTrip(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.getTripById(id));
    }

    @GetMapping
    public ResponseEntity<List<Trip>> getUserTrips(@RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(tripService.getTripsByUserId(userId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Trip> updateStatus(@PathVariable Long id,
                                            @RequestParam Trip.TripStatus status) {
        return ResponseEntity.ok(tripService.updateTripStatus(id, status));
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Long id) {
        byte[] pdf = tripService.generatePdfBudget(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=budget_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}