package com.individual.preonboardingbackend.user.service;

import com.individual.preonboardingbackend.security.jwt.JwtUtil;
import com.individual.preonboardingbackend.user.dto.request.UserLoginRequest;
import com.individual.preonboardingbackend.user.dto.request.UserSignUpRequest;
import com.individual.preonboardingbackend.user.dto.response.UserLoginResponse;
import com.individual.preonboardingbackend.user.dto.response.UserSignUpResponse;
import com.individual.preonboardingbackend.user.model.User;
import com.individual.preonboardingbackend.user.model.UserRole;
import com.individual.preonboardingbackend.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Getter
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;

    }
    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    // 회원가입
    @Override
    public UserSignUpResponse signUp(UserSignUpRequest userSignUpRequest) {

        if (userRepository.existsByUsername(userSignUpRequest.username())) {
            throw new IllegalArgumentException("Username already exists");
        }
        UserRole userRole = UserRole.USER;
        if (userSignUpRequest.isAdmin()) {
            if (!ADMIN_TOKEN.equals(userSignUpRequest.adminToken())) {
                throw new IllegalArgumentException("관리자 암호가 올바르지 않습니다.");
            }
            userRole = UserRole.ADMIN;
        }
        User user = userSignUpRequest.toUser(passwordEncoder, userRole);
        return UserSignUpResponse.from(userRepository.save(user));
    }

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest, HttpServletResponse response) {
        String username = userLoginRequest.username();
        String password = userLoginRequest.password();
        // 유저 찾기
        User user = userRepository.findByUsername(username).orElseThrow(()-> new IllegalArgumentException("유저를 찾지 못함.."));
        // 패스워드 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다."); }
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());

        jwtUtil.addJwtToCookie(token, response);
        return new UserLoginResponse(token);
    }



}
