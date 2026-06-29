package com.example.bankcards.dto.response;

import com.example.bankcards.entity.Card;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
/*
    INFO ABOUT USER
 */
public class UserResponse {
    private Long id;
    private String username;
    private String role;
    private List<CardResponse> cards;
}
