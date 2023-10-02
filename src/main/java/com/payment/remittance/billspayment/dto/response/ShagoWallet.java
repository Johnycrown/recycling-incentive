package com.payment.remittance.billspayment.dto.response;

import lombok.Data;

@Data
public class ShagoWallet {
    public String walletID;
    public String primaryBalance;
    public String commissionBalance;
    public Object created_at;
}
