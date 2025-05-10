package com.smartinventory.auth_service.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String name;     // only used in signup
    private String email;
    private String password;
    private String role;     // optional in signup, ignored in login
}
