package com.payment.remittance.billspayment.service;
import com.payment.remittance.billspayment.dto.request.*;
import com.payment.remittance.billspayment.dto.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
@Slf4j
public class UtilityPaymentServiceImpl implements UtilityPaymentService {
    @Value("${chago.password}")
    private String password;
    @Value("${chago.email}")
    private String email;
    @Value("${chago.baseUrl}")
    private String baseUrl;
    @Value("${chago.hashkey}")
    private String hashKey;

    private final RestTemplate restTemplate;


    public RechargeAirTimeResponse rechargeAirTime(RechargeAirTimeRequest rechargeAirTimeRequest) {
        RechargeAirTimeResponse rechargeAirTimeResponse = callApi(rechargeAirTimeRequest, RechargeAirTimeResponse.class, HttpMethod.POST);
        log.info("rechargeAirtime Response from shago {}", rechargeAirTimeResponse);

        return rechargeAirTimeResponse;
    }



    public TvValidationAndLookUpResponse validationAndBounquestLookUp(TvValidationAndLookUpRequest tvValidationAndLookUpRequest){

        TvValidationAndLookUpResponse tvValidationAndLookUpResponse = callApi(tvValidationAndLookUpRequest, TvValidationAndLookUpResponse.class,HttpMethod.POST);
        log.info("the tv validation look up Bounquest from shago {}", tvValidationAndLookUpResponse);
        return tvValidationAndLookUpResponse;

    }


    public BundleResponse dataBundleLookUp(BundleLookUpRequest bundleLookUpRequest) {

        BundleResponse bundleResponse = callApi(bundleLookUpRequest, BundleResponse.class, HttpMethod.POST);

        log.info("response of bundle look up call from shago {}", bundleResponse);
        return bundleResponse;

    }


    public BuyDataResponse buyData(BuyDataRequest buyDataRequest) {
        BuyDataResponse buyDataResponse = callApi(buyDataRequest, BuyDataResponse.class, HttpMethod.POST);
        log.info("response of buying data from shago {}", buyDataResponse);
        return buyDataResponse;

    }

    public BuyPowerValidationResponse validateBuyPower(BuyPowerRequestValidation buyPowerRequestValidation) {
        BuyPowerValidationResponse buyPowerValidationResponse = callApi(buyPowerRequestValidation, BuyPowerValidationResponse.class, HttpMethod.POST);
        log.info(" response of buy power validation {}", buyPowerValidationResponse);
        return buyPowerValidationResponse;

    }

    public BuyPowerResponse buyPower(BuyPowerRequest buyPowerRequest) {
        BuyPowerResponse response = callApi(buyPowerRequest, BuyPowerResponse.class, HttpMethod.POST);
        log.info("response of calling purchase power end point from shago {}", response);
        return response;
    }

    public TransactionStatusQueryResponse queryTransactionStatus(TransactionStausQueryRequest  transactionStausQueryRequest){
        TransactionStatusQueryResponse response = callApi(transactionStausQueryRequest,TransactionStatusQueryResponse.class,HttpMethod.POST);
        log.info("response of querying transaction status");
        return response;

    }

    public ShagoBalanceResponse fetchWalletBalance(){
        ShagoBalanceRequest request = new ShagoBalanceRequest();
        request.setServiceCode("BAL");
        ShagoBalanceResponse response = callApi(request,ShagoBalanceResponse.class,HttpMethod.POST);
        log.info("response gotten from shago balance inquiry {} ",response);
        return response;

    }


    public <Response, Request> Response callApi(Request request, Class<Response> responseClass, HttpMethod httpMethod) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("email", email);
        headers.add("password", password);
        headers.add("hashKey", hashKey);

        HttpEntity<Request> entity = new HttpEntity<>(request, headers);


        log.info("requesting sending to shago  url \n {} request body\n {} ", baseUrl, entity);

        ResponseEntity<Response> response = restTemplate.exchange(baseUrl, httpMethod, entity, responseClass);

        log.info("response from calling   shago end point  \n {} response  ", response);

        return response.getBody();

    }


}
