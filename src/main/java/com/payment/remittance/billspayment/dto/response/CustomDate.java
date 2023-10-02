package com.payment.remittance.billspayment.dto.response;

import lombok.Data;

@Data
public class CustomDate {
    public String date;
    public int timezone_type;
    public String timezone;
}
