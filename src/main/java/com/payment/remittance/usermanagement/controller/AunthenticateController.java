package com.payment.remittance.usermanagement.controller;
import com.payment.remittance.usermanagement.dto.response.LoginTokenDto;
import com.payment.remittance.usermanagement.dto.response.Tokenizer;
import com.payment.remittance.usermanagement.dto.response.UserInfo;
import com.payment.remittance.usermanagement.model.ClientAccesCredentials;
import com.payment.remittance.usermanagement.repository.ClientAccessCredRepository;
import com.payment.remittance.usermanagement.service.JWTServiceImpl;
import com.payment.remittance.utils.MobileSecurityUtil;
import com.payment.remittance.utils.OtaUtility;
import com.payment.remittance.utils.ResponseCodes;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Slf4j
public class AunthenticateController {

    @Autowired
    ClientAccessCredRepository accessKeysRepository;



    @Autowired
    JWTServiceImpl jwtService;

    @Autowired
    Environment env;


    public UserInfo validateAPIClientAndSecretKey(HttpServletRequest request)
    {
        UserInfo result = new UserInfo();
        result.setResponseCode(ResponseCodes.INVALID_CLIENT_ID_OR_SECRET);
        try
        {
            //LoggingUtil.DebugInfo("x-client-id " + request.getHeader("x-client-id") + " " );
            String sourceCode = request.getHeader("x-source-code");
            String clientId = request.getHeader("x-client-id");
            String clientSecret = request.getHeader("x-client-secret");

            String ipClient = request.getHeader("X-FORWARDED-FOR");
            if (ipClient == null) {
                ipClient = request.getRemoteAddr();
            }

            log.info("source-code " + sourceCode);

            log.info("source-ip:;  " + ipClient);

            log.info("Receive API client id and   " + clientId + " " + ipClient );

            ClientAccesCredentials apiKey =  accessKeysRepository.findByClientId(sourceCode);
            if(apiKey == null)
            {
                log.info("invalid client id not found");
                result.setResponseCode(ResponseCodes.API_KEY_NOT_FOUND);
                return result;
            }

            if(ipClient == null)
                ipClient = "";

            String ip = apiKey.getIpAddress();
            if(ip == null)
                ip ="";

            log.info("valid source ip in db " + ip);

            if(!ipClient.equals("") || !ip.equals("") || ip.contains(ipClient))
            {
                log.info("Receive Loan app update notify - IP is valid " + ipClient + " " + ip );
            }
            else {
                result.setResponseCode(ResponseCodes.INVALID_IP_ADDRESS);
                return result;
            }

            if(apiKey.getApiKey() != null && !apiKey.getApiKey().equals(""))
            {
                String clientSecretB = MobileSecurityUtil.decrypt(apiKey.getApiKey(),"");
                String clientIdB = MobileSecurityUtil.decrypt(apiKey.getPublicKey(),"");
                //LoggingUtil.DebugInfo("S-KEYY  " + authType + " " + storeKey);
                if(clientSecretB.equals(clientSecret) && clientIdB.equals(clientId) && !clientId.equals("") )
                {

                    log.info("Client Id & Secret matched ");
                    result.setResponseCode(ResponseCodes.SUCCESS);
                    result.setEntityCode(apiKey.getEntityCode());
                    result.setClientId(apiKey.getClientId());
                    result.setLanguage("en");

                }
                else {
                    result.setResponseCode(ResponseCodes.INVALID_CLIENT_ID_OR_SECRET);
                    return result;
                }

            }
            else {
                result.setResponseCode(ResponseCodes.API_KEY_NOT_FOUND);
                return result;
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return result;
    }

    public LoginTokenDto login(HttpServletRequest request)
    {
        LoginTokenDto result = new LoginTokenDto();
        result.setCode(ResponseCodes.INVALID_CLIENT_ID_OR_SECRET);
        result.setDesc("invalid client id or client secret");
        try
        {
            //LoggingUtil.DebugInfo("x-client-id " + request.getHeader("x-client-id") + " " );
            String sourceCode = request.getHeader("x-source-code");
            String clientId = request.getHeader("x-client-id");
            String clientSecret = request.getHeader("x-client-secret");

            String ipClient = request.getHeader("X-FORWARDED-FOR");
            if (ipClient == null) {
                ipClient = request.getRemoteAddr();
            }

            log.info("source-code " + sourceCode);

            log.info("source-ip:;  " + ipClient);

            log.info("Receive API client id and   " + clientId + " " + ipClient );

            ClientAccesCredentials apiKey =  accessKeysRepository.findByClientId(sourceCode);
            if(apiKey == null)
            {
                log.info("invalid client id not found");
                result.setCode(ResponseCodes.API_KEY_NOT_FOUND);
                return result;
            }

            if(ipClient == null)
                ipClient = "";

            String ip = apiKey.getIpAddress();
            if(ip == null)
                ip ="";

            log.info("valid source ip in db " + ip);

            if(!ipClient.equals("") || !ip.equals("") || ip.contains(ipClient))
            {
                log.info("Receive Loan app update notify - IP is valid " + ipClient + " " + ip );
            }
            else {
                result.setCode(ResponseCodes.INVALID_IP_ADDRESS);
                return result;
            }

            if(apiKey.getApiKey() != null && !apiKey.getApiKey().equals(""))
            {
                String clientSecretB = MobileSecurityUtil.decrypt(apiKey.getApiKey(),"");
                String clientIdB = MobileSecurityUtil.decrypt(apiKey.getPublicKey(),"");
                //LoggingUtil.DebugInfo("S-KEYY  " + authType + " " + storeKey);
                if(clientSecretB.equals(clientSecret) && clientIdB.equals(clientId) && !clientId.equals("") )
                {

                    log.info("Client Id & Secret matched ");
                    result.setCode(ResponseCodes.SUCCESS);
                    int timeExp = Integer.parseInt(env.getProperty("app.jwtTokenExpirationHour")); //in hour
                    long timeInMillis = 1000 * 60 * 60 * timeExp;
                    String token = jwtService.generateAppJWT(apiKey.getSourceCode(), apiKey.getEntityCode(),apiKey.getClientId(), "en",timeInMillis);
                    result.setToken(token);
                    result.setSourceCode(sourceCode);
                    result.setDesc("successful");
                    apiKey.setToken(token);
                    apiKey.setTokenExpiryDate(OtaUtility.AddTimeToDate(new Date(),60 * timeExp));
                    accessKeysRepository.save(apiKey);



                }
                else {
                    result.setCode(ResponseCodes.INVALID_CLIENT_ID_OR_SECRET);
                    return result;
                }

            }
            else {
                result.setCode(ResponseCodes.API_KEY_NOT_FOUND);
                return result;
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return result;
    }
    protected UserInfo validateFromTokenToObject(HttpServletRequest request) {
        return validateFromTokenToObject(request,"","");
    }
    protected UserInfo validateFromTokenToObject(HttpServletRequest request, String permissionCode, String deviceId) {
        UserInfo accessKeyInfo = new UserInfo();
        accessKeyInfo.setResponseCode(ResponseCodes.INVALID_JWT_TOKEN);
        accessKeyInfo.setResponseMessage("the token provided is not valid");
        String authToken = request.getHeader("Authorization");
        if (authToken == null && request.getAttribute("x-auth-token") != null)
            authToken = request.getAttribute("x-auth-token").toString();


        if (authToken == null || authToken.equals("")) {
            return accessKeyInfo;
            //throw new UserAuthenticationException("Authorization credentials not provided(x-auth-token)");
        }

        String sourceCode = request.getHeader("x-source-code");

        ClientAccesCredentials apiKey =  accessKeysRepository.findByClientId(sourceCode);
        if(apiKey == null)
        {

            log.info("client id not found");
            accessKeyInfo.setResponseCode(ResponseCodes.INVALID_SOURCE_CODE);
            return accessKeyInfo;
        }

        try {
            authToken = authToken.replace("Bearer ", "").trim();
            Claims claims = jwtService.parseJWT(authToken);
            //GacUserProfiles user = userService.findUserFromCache(claims.getId());
            if (claims.getId() != null) {
                String[] ids = Tokenizer.tokenize(claims.getId(), "|");
                System.out.println("Token-base-validate " + claims.getId());
                //sourceCode + "|" + entityCode + "|" + clientId + "|" + language;

                String entityCode = ids[1];
                String clientId = ids[2];
                String lang = ids[3];

                if (!apiKey.getToken().equals(authToken)) {
                    accessKeyInfo.setResponseCode(ResponseCodes.INVALID_JWT_TOKEN);
                    accessKeyInfo.setResponseMessage("invalid token provided");
                    return accessKeyInfo;
                }
                if(apiKey.getTokenExpiryDate().getTime() < new Date().getTime())
                {
                    accessKeyInfo.setResponseCode(ResponseCodes.JWT_TOKEN_EXPIRED);
                    accessKeyInfo.setResponseMessage("the token provided has expired");
                    return accessKeyInfo;
                }

                accessKeyInfo.setEntityCode(entityCode);
                accessKeyInfo.setLanguage(lang);
                accessKeyInfo.setResponseCode(ResponseCodes.SUCCESS);

                return accessKeyInfo;
            }
            return null;
        } catch (ExpiredJwtException eje) {
            log.info("JWT-User-Token-expired,please login again");


        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex) {
            log.info("JWT-Invalid Token , please login again");

        }

        return accessKeyInfo;
    }
}
