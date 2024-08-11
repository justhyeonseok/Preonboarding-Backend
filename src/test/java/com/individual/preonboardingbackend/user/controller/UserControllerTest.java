package com.individual.preonboardingbackend.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.individual.preonboardingbackend.user.dto.request.UserLoginRequest;
import com.individual.preonboardingbackend.user.dto.request.UserSignUpRequest;
import com.individual.preonboardingbackend.user.dto.response.UserLoginResponse;
import com.individual.preonboardingbackend.user.dto.response.UserSignUpResponse;
import com.individual.preonboardingbackend.user.model.UserRole;
import com.individual.preonboardingbackend.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
    }

    @Test
    public void testSignUp() throws Exception {
        // given
        UserSignUpRequest signUpRequest = new UserSignUpRequest("testUser", "password", "nickname", false, null);
        UserSignUpResponse signUpResponse = new UserSignUpResponse("testUser", "nickname", UserRole.USER);

        Mockito.when(userService.signUp(any(UserSignUpRequest.class))).thenReturn(signUpResponse);

        // when & then
        mockMvc.perform(post("/api/v1/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("testUser")))
                .andExpect(jsonPath("$.nickname", is("nickname")))
                .andExpect(jsonPath("$.userRole", is("USER")));
    }

    @Test
    public void testLogin() throws Exception {
        // given
        UserLoginRequest loginRequest = new UserLoginRequest("testUser", "password");
        UserLoginResponse loginResponse = new UserLoginResponse("token");

        Mockito.when(userService.login(any(UserLoginRequest.class), any(HttpServletResponse.class))).thenReturn(loginResponse);

        // when & then
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token", is("token")));
    }
}
