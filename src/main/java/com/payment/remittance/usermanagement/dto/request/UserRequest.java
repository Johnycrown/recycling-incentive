package com.payment.remittance.usermanagement.dto.request;

import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String residentialAddress;
}
