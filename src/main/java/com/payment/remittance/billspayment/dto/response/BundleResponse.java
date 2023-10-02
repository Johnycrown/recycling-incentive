package com.payment.remittance.billspayment.dto.response;

import lombok.Data;

import java.util.ArrayList;

@Data
public class BundleResponse {
    public String message;
    public String status;
    public ArrayList<DataProduct> product;
    public Object phone;
    public String network;
}
