package com.individual.preonboardingbackend.user.controller;

import com.individual.preonboardingbackend.user.dto.UserRequest;
import com.individual.preonboardingbackend.user.dto.UserResponse;
import com.individual.preonboardingbackend.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(
            @RequestBody UserRequest userRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.signUp(userRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest userRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.login(userRequest));
    }

}
