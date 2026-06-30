package com.example.bankcards.exception;

import java.math.BigDecimal;

public class InsufficientBalanceException extends CustomException {

    public InsufficientBalanceException(Long id, BigDecimal balance, BigDecimal amount) {
        super(String.format("Недостаточно средств на карте %d. Баланс: %.2f. Сумма перевода: %.2f.",
                id, balance, amount));
    }
}
