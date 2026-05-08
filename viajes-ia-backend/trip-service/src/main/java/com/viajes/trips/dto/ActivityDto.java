package com.viajes.trips.dto;

public record ActivityDto(
    String time,
    String description,
    String location,
    Double estimatedCost,
    String category
) {}