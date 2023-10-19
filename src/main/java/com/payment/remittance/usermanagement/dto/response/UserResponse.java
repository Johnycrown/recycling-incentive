package com.payment.remittance.usermanagement.dto.response;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String residentialAddress;
}
