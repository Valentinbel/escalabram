package com.escalabram.escalabram.security.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequest {
    @NotBlank
    @Size(min = 4, max = 20, message = "userName should be between 4 to 20 characters")
    private String userName;

    @NotBlank
    @Size(max = 50, message = "Email cannot be longer than 50 characters")
    @Email(message = "Email should be valid")
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 8, max = 40, message = "Password should be between 8 to 40 characters")
    private String password;

    @NotNull(message = "Language cannot be null")
    private Long languageId;
}