package com.payment.remittance.billspayment.dto.response;

import lombok.Data;

@Data
public class RechargeAirTimeResponse {
    public String message;
    public String status;
    public int amount;
    public String transId;
    public String type;
    public String date;
    public String phone;

}
