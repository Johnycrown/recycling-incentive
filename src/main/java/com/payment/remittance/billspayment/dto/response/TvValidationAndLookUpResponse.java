package com.payment.remittance.billspayment.dto.response;


import com.payment.remittance.billspayment.dto.request.TvProduct;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class TvValidationAndLookUpResponse {
    public String status;
    public String message;
    public String type;
    public String smartCardNo;
    public String customerName;
    public String accountStatus;
    public Date dueDate;
    public int invoicePeriod;
    public int customerNumber;
    public Object balance;
    public Object custormerType;
    public ArrayList<TvProduct> product;

}
