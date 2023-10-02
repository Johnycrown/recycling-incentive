package com.payment.remittance.billspayment.dto.response;

import lombok.Data;

@Data
public class BuyPowerValidationResponse {
    public String meterNo;
    public String accountNo;
    public String customerName;
    public String customerAddress;
    public String customerDistrict;
    public String phoneNumber;
    public Object minimumAmount;
    public String type;
    public String disco;
    public String status;
}
