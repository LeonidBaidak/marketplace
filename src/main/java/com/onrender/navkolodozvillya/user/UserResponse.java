package com.onrender.navkolodozvillya.user;

import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        Role role) {
}
