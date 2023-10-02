package com.payment.remittance.billspayment.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BuyDataResponse {
    public int amount;
    public String message;
    public String status;
    public String transId;
    public String date;
    public String phone;
    @JsonProperty("package")
    public String mypackage;
}
