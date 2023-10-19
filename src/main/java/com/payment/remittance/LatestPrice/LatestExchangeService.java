package com.payment.remittance.LatestPrice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class LatestExchangeService {

    @Value("${Ceio.url}")
    private static String baseurl;





    private final static RestTemplate restTemplate = new RestTemplate();




    public static PriceResponse  convertBtcToUsd(){
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        System.out.println("\n\n\n" + baseurl);
    PriceResponse response = restTemplate.getForObject( "https://cex.io/api/last_price/BTC/USD", PriceResponse.class);
    return response;


   }


    public static BigDecimal getDollarEquivalent(BigDecimal btcValue) {
        PriceResponse response = LatestExchangeService.convertBtcToUsd();

        return btcValue.multiply(BigDecimal.valueOf(Double.parseDouble(response.getLprice())));


    }
}
