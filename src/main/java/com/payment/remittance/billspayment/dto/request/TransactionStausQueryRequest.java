package com.payment.remittance.billspayment.dto.request;

import lombok.Data;

@Data
public class TransactionStausQueryRequest {
    public String serviceCode;
    public String reference;

}
