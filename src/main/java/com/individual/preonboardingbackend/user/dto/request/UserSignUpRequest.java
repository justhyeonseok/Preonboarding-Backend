package com.individual.preonboardingbackend.user.dto.request;

import com.individual.preonboardingbackend.user.model.User;
import com.individual.preonboardingbackend.user.model.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;

public record UserSignUpRequest(
        String username,
        String password,
        String nickname,
        boolean isAdmin,
        String adminToken
) {
    public User toUser(PasswordEncoder passwordEncoder, UserRole role) {
        String encodedPassword = passwordEncoder.encode(this.password);
        return new User(
                username,
                nickname,
                encodedPassword,
                role
        );
    }
}