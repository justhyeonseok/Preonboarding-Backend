package com.individual.preonboardingbackend.user.service;

import com.individual.preonboardingbackend.user.dto.UserRequest;
import com.individual.preonboardingbackend.user.dto.UserResponse;


public interface UserService {
    UserResponse signUp(UserRequest userRequest);
    UserResponse login(UserRequest userRequest);
}
