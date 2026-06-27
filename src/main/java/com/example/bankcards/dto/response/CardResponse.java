package com.example.bankcards.dto.response;

import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CardResponse {
    private Long id;
    private User user;
    private String number; // в формате **** **** **** 1234
    private LocalDate date;
    private String status;
    private BigDecimal balance;
}
