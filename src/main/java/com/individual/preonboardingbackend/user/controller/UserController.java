package com.individual.preonboardingbackend.user.controller;

import com.individual.preonboardingbackend.user.dto.request.UserLoginRequest;
import com.individual.preonboardingbackend.user.dto.response.UserLoginResponse;
import com.individual.preonboardingbackend.user.dto.request.UserSignUpRequest;
import com.individual.preonboardingbackend.user.dto.response.UserSignUpResponse;
import com.individual.preonboardingbackend.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponse> signUp(
            @RequestBody UserSignUpRequest userSignUpRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.signUp(userSignUpRequest));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(
            @RequestBody UserLoginRequest userLoginRequest,
            HttpServletResponse response
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.login(userLoginRequest, response));
    }

}
