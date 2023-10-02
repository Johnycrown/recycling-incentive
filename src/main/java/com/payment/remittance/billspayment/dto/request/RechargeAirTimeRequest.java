package com.payment.remittance.billspayment.dto.request;

import lombok.Data;

@Data
public class RechargeAirTimeRequest {
    public String serviceCode;
    public String phone;
    public String amount;
    public String vend_type;
    public String network; //The network names are "MTN", "AIRTEL", "GLO" and "9mobile"
    public String request_id;

}
