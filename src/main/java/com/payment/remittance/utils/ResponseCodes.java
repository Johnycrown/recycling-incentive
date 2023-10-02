package com.payment.remittance.utils;

import lombok.Data;

@Data
public class ResponseCodes {
    public static final String INVALID_IP_ADDRESS = "E43";
    public static final String API_KEY_NOT_FOUND = "E44";
    public static final String INVALID_JWT_TOKEN = "E18";
    public static final String INVALID_SOURCE_CODE = "E19";
    public static final String NO_RECORD_FOUND = "E20";
    public static final String JWT_TOKEN_EXPIRED = "E21";
    public static final String SUCCESS = "000";
    public static final String INVALID_CLIENT_ID_OR_SECRET = "E22";
}
