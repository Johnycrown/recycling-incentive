package com.payment.remittance.usermanagement.service;

import io.jsonwebtoken.Claims;

public interface JWTService {
    String createJWT(String id, String issuer, String subject, long ttlMillis) throws Exception;
    Claims parseJWT(String jwt) throws Exception;
    String generatePublicJWT(String sourceCode,String username,String channel);
    String validatePublicJWT(String token, String username);
}
