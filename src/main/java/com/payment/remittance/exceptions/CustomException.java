package com.payment.remittance.exceptions;

public class CustomException extends IllegalArgumentException{
    public CustomException(String message) {
        super(message);
    }
}
