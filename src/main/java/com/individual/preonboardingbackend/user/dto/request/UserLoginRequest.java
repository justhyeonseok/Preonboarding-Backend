package com.individual.preonboardingbackend.user.dto.request;

public record UserLoginRequest(
        String username,
        String password
) {

}