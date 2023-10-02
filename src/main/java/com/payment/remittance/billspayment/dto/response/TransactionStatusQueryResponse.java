package com.payment.remittance.billspayment.dto.response;

import lombok.Data;

@Data
public class TransactionStatusQueryResponse {
    public String status;
    public String message;
    public String amount;
    public CustomDate date;
    public String transId;
    public String type;
    public String phone;
}
