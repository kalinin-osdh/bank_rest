package com.example.bankcards.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
/*
    RESPONSE AUTH USER WITH JWT TOKEN
*/
public class AuthResponse {
    private String token;
    private String username;
    private String password;
}
