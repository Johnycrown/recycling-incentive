package com.payment.remittance.billspayment.dto.request;

import com.accelerexgroup.baascore.modules.billpayment.dto.BillerPaymentData;
import lombok.Data;

import java.util.List;

@Data
public class ValidateBillRequest {

    private String entityCode;
    private String billerCode;
    private String productCode;
    private String referenceNo;
    private double amount;
    private String countryCode;
    private String serviceProvider;

    private List<BillerPaymentData> paymentDataList;

}
