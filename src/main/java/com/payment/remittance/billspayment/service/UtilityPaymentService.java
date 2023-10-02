package com.payment.remittance.billspayment.service;

import com.payment.remittance.billspayment.dto.request.*;
import com.payment.remittance.billspayment.dto.response.*;

public interface UtilityPaymentService {
    public RechargeAirTimeResponse rechargeAirTime(RechargeAirTimeRequest rechargeAirTimeRequest) ;



    public TvValidationAndLookUpResponse validationAndBounquestLookUp(TvValidationAndLookUpRequest tvValidationAndLookUpRequest);


    public BundleResponse dataBundleLookUp(BundleLookUpRequest bundleLookUpRequest);

    public BuyDataResponse buyData(BuyDataRequest buyDataRequest);

    public BuyPowerValidationResponse validateBuyPower(BuyPowerRequestValidation buyPowerRequestValidation);

    public BuyPowerResponse buyPower(BuyPowerRequest buyPowerRequest);

    public TransactionStatusQueryResponse queryTransactionStatus(TransactionStausQueryRequest  transactionStausQueryRequest);

    public ShagoBalanceResponse fetchWalletBalance();
}
