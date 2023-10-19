package com.payment.remittance.bitcoin.dto;

import lombok.Data;

@Data
public class WithdrawalRequest {

    private String depositAddress;
    private String network;
    private Double amount;
    private Long userId;
}
