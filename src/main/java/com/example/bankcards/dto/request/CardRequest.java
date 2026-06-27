package com.example.bankcards.dto.request;

import com.example.bankcards.entity.User;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CardRequest {
    @NotNull(message = "User cannot be null")
    private User user;

    @NotBlank(message = "Card number cannot be null")
    @Size(min = 16, max = 255, message = "Card must be 16 characters")
    private String number;

    @NotNull(message = "Expiry date cannot be null")
    @Future(message = "Expiry date must be in the future")
    private LocalDate date;
}
