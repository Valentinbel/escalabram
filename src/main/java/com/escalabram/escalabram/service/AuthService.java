package com.escalabram.escalabram.service;

import com.escalabram.escalabram.security.payload.request.LoginRequest;
import com.escalabram.escalabram.security.payload.request.SignupRequest;
import com.escalabram.escalabram.security.payload.response.JwtResponse;
import com.escalabram.escalabram.model.ClimberUser;

public interface AuthService {

    ClimberUser createUser(SignupRequest signUpRequest);

    JwtResponse authenticateUser(LoginRequest loginRequest);
}
