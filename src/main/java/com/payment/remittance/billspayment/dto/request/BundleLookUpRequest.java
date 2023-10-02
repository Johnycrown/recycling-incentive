package com.payment.remittance.billspayment.dto.request;

import lombok.Data;

@Data
public class BundleLookUpRequest {
    public String serviceCode;
    public String phone;
    public String network;
}
