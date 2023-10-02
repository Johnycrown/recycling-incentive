package com.payment.remittance.billspayment.dto.request;

import lombok.Data;

@Data
public class BuyPowerRequestValidation {
    public String serviceCode;
    public String disco;
    public String meterNo;
    public String type;


}
