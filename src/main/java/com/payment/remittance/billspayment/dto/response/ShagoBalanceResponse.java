package com.payment.remittance.billspayment.dto.response;

import lombok.Data;

@Data
public class ShagoBalanceResponse {
    public String status;
    public String message;
    public ShagoWallet wallet;
}
