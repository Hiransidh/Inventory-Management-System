package com.smartinventory.auth_service.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthResponse {
    // Getters and setters
    private String token;
    private String role;

    // Default constructor
    public AuthResponse() {
    }

    // Constructor with arguments
    public AuthResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }

}