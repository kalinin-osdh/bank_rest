package com.example.bankcards.exception;

public class TransferSameCardException extends CustomException {

    public TransferSameCardException(Long id) {
        super("Попытка перевода на ту же карту: " + id);
    }
}
