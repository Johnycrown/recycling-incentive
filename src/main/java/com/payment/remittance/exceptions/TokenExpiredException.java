package com.payment.remittance.exceptions;

public class TokenExpiredException extends CustomException {

    public TokenExpiredException(String message) {
        super(message);
    }
}
