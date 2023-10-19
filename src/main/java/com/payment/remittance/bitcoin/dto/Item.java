package com.payment.remittance.bitcoin.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Item {
    public String callbackSecretKey;
    public String callbackUrl;
    public String feePriority;
    public String note;
    public String prepareStrategy;
    public ArrayList<Recipient> recipients;
}
