package com.example.bankcards.dto.request;

import com.example.bankcards.entity.User;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CardRequest {
    @NotNull(message = "User id cannot be null")
    @Positive(message = "User id cannot be negative")
    private Long userId;

    @NotBlank(message = "Card number cannot be null")
    @Size(min = 16, max = 255, message = "Card must be 16 characters")
    private String number;

    @NotNull(message = "Expiry date cannot be null")
    @Future(message = "Expiry date must be in the future")
    private LocalDate date;
}
