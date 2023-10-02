package com.payment.remittance.usermanagement.dto.response;

import lombok.Data;

@Data
public class UserInfo {
    private String responseCode;
    private String responseMessage;
    private String email;
    private String clientId;
    private String entityCode;
    private String language;
}
