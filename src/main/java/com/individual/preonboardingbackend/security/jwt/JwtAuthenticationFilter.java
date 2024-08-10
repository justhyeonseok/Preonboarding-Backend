package com.individual.preonboardingbackend.security.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.individual.preonboardingbackend.security.UserDetailsImpl;
import com.individual.preonboardingbackend.user.dto.request.UserLoginRequest;
import com.individual.preonboardingbackend.user.model.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성 ")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/v1/user/login");
    }
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        log.info("Attempting authentication");

        try {
            UserLoginRequest requestDto = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequest.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.username(),
                            requestDto.password(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error("Authentication error: {}", e.getMessage());
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        log.info("JWT Authentication Successful");
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRole role = ((UserDetailsImpl) authResult.getPrincipal()).user().getRole();
        String token = jwtUtil.createToken(username, role);
        jwtUtil.addJwtToCookie(token, response);
        chain.doFilter(request, response);
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) {
        log.info("JWT Authentication Failed");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 추가적인 오류 메시지나 처리가 필요하다면 여기에 추가
    }
}

