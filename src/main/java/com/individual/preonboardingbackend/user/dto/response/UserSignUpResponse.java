package com.individual.preonboardingbackend.user.dto.response;

import com.individual.preonboardingbackend.user.model.User;
import com.individual.preonboardingbackend.user.model.UserRole;

public record UserSignUpResponse(
        String username,
        String nickname,
        UserRole userRole
) {
    public static UserSignUpResponse from(User user) {
        return new UserSignUpResponse(
                user.getUsername(),
                user.getNickname(),
                user.getRole()
        );
    }

}
