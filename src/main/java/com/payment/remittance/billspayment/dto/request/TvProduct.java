package com.payment.remittance.billspayment.dto.request;

import lombok.Data;

@Data
public class TvProduct {
    public String name;
    public String code;
    public int month;
    public int price;
    public int period;
}
