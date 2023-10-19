package com.payment.remittance.bitcoin.dto;

import lombok.Data;

@Data
public class Recipient {
    public String address;
    public String amount;
}
