package com.payment.remittance.billspayment.dto.response;

import lombok.Data;

@Data
public class BuyPowerResponse {
    public String token;
    public Object configureToken;
    public Object resetToken;
    public String unit;
    public double taxAmount;
    public Object tokenAmount;
    public Object bonusUnit;
    public Object bonusToken;
    public Object debtPayment;
    public Object minimumAmount;
    public Object arrear;
    public Object arrearsApplied;
    public int amount;
    public String message;
    public String status;
    public String customerName;
    public String customerAddress;
    public String date;
    public String transId;
    public String disco;


}
