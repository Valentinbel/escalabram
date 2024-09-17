package com.escalabram.escalabram.service;

import com.escalabram.escalabram.configuration.payload.request.LoginRequest;
import com.escalabram.escalabram.configuration.payload.request.SignupRequest;
import com.escalabram.escalabram.configuration.payload.response.JwtResponse;
import com.escalabram.escalabram.model.ClimberUser;

public interface AuthService {

    ClimberUser createUser(SignupRequest signUpRequest);

    JwtResponse authenticateUser(LoginRequest loginRequest);
}
