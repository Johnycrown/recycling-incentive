package com.payment.remittance.usermanagement.dto.response;

import lombok.Data;

@Data
public class LoginTokenDto {

    private String code;
    private String desc;
    private String token;
    private String sourceCode;

}
