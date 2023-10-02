package com.payment.remittance.exceptions;

public class InvalidLoginDetailsException extends CustomException{
    public InvalidLoginDetailsException(String message) {
        super(message);
    }
}
