package com.payment.remittance.billspayment.dto.response;

import lombok.Data;

@Data
public class DataProduct {
    public int price;
    public String code;
    public String allowance;
    public String validity;
}
