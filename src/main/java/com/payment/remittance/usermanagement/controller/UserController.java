package com.payment.remittance.usermanagement.controller;

import com.payment.remittance.usermanagement.dto.request.UserRequest;
import com.payment.remittance.usermanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController extends AunthenticateController{

    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequest request){

        return ResponseEntity.ok().body(userService.createUser(request));
    }
}
