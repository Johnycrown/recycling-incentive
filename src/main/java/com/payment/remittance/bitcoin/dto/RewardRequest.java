package com.payment.remittance.bitcoin.dto;

import lombok.Data;

@Data

public class RewardRequest {
    private Long userid;
    private long bottleQuantity;
    private long amountOfSat;
}
