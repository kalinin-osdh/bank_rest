package com.example.bankcards.entity;

import com.example.bankcards.entity.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "cards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank(message = "Card number cannot be null")
    @Size(min = 16,max = 255,message = "Card must be 16 characters")
    @Column(unique = true, nullable = false)
    private String number;

    @NotNull(message = "Expiry date cannot be null")
    @Future(message = "Expiry date must be in the future")
    @Column(name = "expiry", nullable = false)
    private LocalDate date;

    @NotNull(message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private Status status = Status.ACTIVE;


    @NotNull(message = "Balance cannot be null")
    @DecimalMin(value = "0.00", message = "Balance cannot be negative")
    @DecimalMax(value = "999999999.99", message = "Balance max limit")
    @Column(precision = 19,scale = 2)
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;
}
