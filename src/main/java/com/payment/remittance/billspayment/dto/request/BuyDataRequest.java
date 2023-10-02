package com.payment.remittance.billspayment.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BuyDataRequest {
    public String serviceCode;
    public String phone;
    public String amount;
    public String bundle;
    public String network;
    @JsonProperty("package")
    public String mypackage;
    public String request_id;
}
