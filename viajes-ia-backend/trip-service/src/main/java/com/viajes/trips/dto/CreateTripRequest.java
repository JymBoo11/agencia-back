package com.viajes.trips.dto;

import java.util.List;

public record CreateTripRequest(
    String destination,
    List<DayItineraryDto> itinerary,
    Double totalPrice,
    String priceBreakdownJson
) {}