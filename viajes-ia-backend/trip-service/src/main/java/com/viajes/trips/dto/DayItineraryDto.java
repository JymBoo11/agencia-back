package com.viajes.trips.dto;

import java.util.List;

public record DayItineraryDto(
    int day,
    String title,
    List<ActivityDto> activities,
    String accommodation,
    String notes
) {}