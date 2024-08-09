package com.individual.preonboardingbackend.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequest {
    private String userName;
    private String password;
    private String nickName;
}
