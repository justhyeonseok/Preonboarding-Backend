package com.individual.preonboardingbackend.user.service;

import com.individual.preonboardingbackend.user.dto.request.UserLoginRequest;
import com.individual.preonboardingbackend.user.dto.response.UserLoginResponse;
import com.individual.preonboardingbackend.user.dto.request.UserSignUpRequest;
import com.individual.preonboardingbackend.user.dto.response.UserSignUpResponse;
import jakarta.servlet.http.HttpServletResponse;


public interface UserService {
    UserSignUpResponse signUp(UserSignUpRequest userSignUpRequest);
    UserLoginResponse login(UserLoginRequest userLoginRequest, HttpServletResponse response);
}
