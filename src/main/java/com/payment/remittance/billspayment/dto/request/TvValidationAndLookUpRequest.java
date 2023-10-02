package com.payment.remittance.billspayment.dto.request;

import lombok.Data;

@Data
public class TvValidationAndLookUpRequest {
    public String serviceCode;
    public String smartCardNo;
    public String type;

}
