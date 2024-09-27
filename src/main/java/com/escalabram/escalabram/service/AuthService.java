package com.escalabram.escalabram.service;

import com.escalabram.escalabram.security.payload.request.LoginRequest;
import com.escalabram.escalabram.security.payload.request.SignupRequest;
import com.escalabram.escalabram.security.payload.request.TokenRefreshRequest;
import com.escalabram.escalabram.security.payload.response.JwtResponse;
import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.security.payload.response.MessageResponse;
import com.escalabram.escalabram.security.payload.response.TokenRefreshResponse;

public interface AuthService {

    ClimberUser createUser(SignupRequest signUpRequest);

    JwtResponse authenticateUser(LoginRequest loginRequest);

    TokenRefreshResponse refreshtoken(TokenRefreshRequest request);

    MessageResponse logoutUser();


}
