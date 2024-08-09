package com.individual.preonboardingbackend.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(force = true)
public class UserResponse {
    private String userName;
    private String nickName;
    private List<String> authorities;
}
