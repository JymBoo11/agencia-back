package com.viajes.users.dto;

public record AuthResponse(String token, String email, Long userId, String firstName) {
}