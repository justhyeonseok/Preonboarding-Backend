package com.individual.preonboardingbackend.user.model;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String role;

    private UserRole(String role) {
        this.role = role;
    }
}
