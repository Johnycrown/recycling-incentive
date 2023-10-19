package com.payment.remittance.usermanagement.service;

import com.payment.remittance.bitcoin.dto.Userbalance;
import com.payment.remittance.usermanagement.dto.request.UserRequest;
import com.payment.remittance.usermanagement.dto.response.UserResponse;
import com.payment.remittance.usermanagement.model.AppUser;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);
    UserResponse fetchUserByEmail(String userName);

    Userbalance fetchUserBalance(long userId);


}
