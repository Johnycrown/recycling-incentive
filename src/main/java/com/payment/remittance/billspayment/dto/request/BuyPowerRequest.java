package com.payment.remittance.billspayment.dto.request;

import lombok.Data;

@Data
public class BuyPowerRequest {
    public String serviceCode;
    public String disco;
    public String meterNo;
    public String type;
    public String amount;
    public String phonenumber;
    public String name;
    public String address;
    public String request_id;
}
