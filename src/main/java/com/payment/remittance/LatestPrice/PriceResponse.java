package com.payment.remittance.LatestPrice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PriceResponse {
    private  String lprice;
    private String curr1;
    private String curr2;
}
