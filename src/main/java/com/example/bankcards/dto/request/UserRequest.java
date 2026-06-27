package com.example.bankcards.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
/*
    SIGN IN
    SIGN UP
 */
public class UserRequest {
    @NotBlank(message = "Username cannot be null")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20")
    private String username;
    @NotBlank(message = "Password cannot be null")
    @Size(min = 8, max = 255, message = "Username must be at least 8")
    private String password;
}
