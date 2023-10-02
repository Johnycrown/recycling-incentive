package com.payment.remittance.usermanagement.service;


import com.payment.remittance.config.PaymentAppConfig;
import com.payment.remittance.usermanagement.dto.response.Tokenizer;
import com.payment.remittance.usermanagement.dto.response.WebResponseInfo;
import com.payment.remittance.utils.ResponseCodes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
@Component
public class JWTServiceImpl implements JWTService{

    @Autowired
    private PaymentAppConfig config;

    @Autowired
    private Environment environment;



    public String generatePublicJWT(String sourceCode,String username, String channel)
    {
        int timeExp = 0;
        if(channel != null && channel.equals("WEB"))
            timeExp = Integer.parseInt(environment.getProperty("app.jwtTokenExpirationHour"));
        else
            timeExp = Integer.parseInt(environment.getProperty("app.jwtTokenExpirationHour")); //Settings.getInstance("").getProperty("app.jwtTokenExpiration"));

        long timeInMillis = 1000 * 60 * timeExp; //In minutes
        String token = createJWT( username, sourceCode, "PUBLIC_PROFILE_TOKEN", timeInMillis);

        return token;
    }

    public String generatePublicJWT(String sourceCode,String username, String subject, int expirationTimeInMillSec)
    {
        int timeExp = expirationTimeInMillSec;

        long timeInMillis = 1000 * 60 * timeExp;
        String token = createJWT( username, sourceCode, subject, timeInMillis);

        return token;
    }

    public String generateAppJWT(String sourceCode,String entityCode,String clientId,String language,long timeInMillis)
    {

        String id = sourceCode + "|" + entityCode + "|" + clientId + "|" + language;

        String token = createJWT( id, sourceCode, "PUBLIC_PROFILE_TOKEN", timeInMillis);

        return token;
    }

    public String generatePublicJWT(String sourceCode,String username,String channel,
                                    String entityCode, String role,String language)
    {
        int timeExp = Integer.parseInt(environment.getProperty("app.jwtTokenExpiration")); // Integer.parseInt(Settings.getInstance("").getProperty("app.jwtTokenExpiration"));
        String id = sourceCode + "|" + username + "|" + channel + "|" + entityCode + "|" + role + "|" + language;
        long timeInMillis = 1000 * 60 * timeExp;
        String token = createJWT( id, sourceCode, "PUBLIC_PROFILE_TOKEN", timeInMillis);

        return token;
    }
    public String validatePublicJWT(String token)
    {
        return validatePublicJWT(token,"");
    }
    public String  validatePublicJWT(String token, String username)
    {
        WebResponseInfo rspWebInfo = new WebResponseInfo();

        rspWebInfo.setCode(ResponseCodes.INVALID_JWT_TOKEN);

        token = token.replace("Bearer ","").trim();
        //System.out.println("Token- " + token);

        //RspWebInfo rspWebInfo = new RspWebInfo();
        //ServiceResponse<String> resp = new ServiceResponse<String>();
        //resp.setCode(ResponseCodes.INVALID_JWT_TOKEN);

        String rspCode = ResponseCodes.SUCCESS;
        Claims claims = parseJWT(token);

        //System.out.println("Token-claims " + xs.toXML(claims));
        //System.out.println("token id :  " + claims.getId());
        long cTime = new Date().getTime();
        long eTime = claims.getExpiration().getTime();
        if(eTime <= cTime)
        {
            System.out.println("token expired:  " + cTime + "  " + eTime  );
            return ResponseCodes.JWT_TOKEN_EXPIRED;
            //throw new UserAuthenticationException("Token Expired , please login again ");
        }
        String[] ids = Tokenizer.tokenize(claims.getId(),"|");
        String usernamex = "";
        // if(ids.length > 1)
        //usernamex = ids[1];

        // System.out.println("Validate token :  " + claims.getId() + " " + claims.getIssuer() + " " + claims + " " + claims.getExpiration());
        //GacUserProfile user = userService.findUserFromCache(claims.getId());

        return  rspCode;
    }

    public String  createJWT(String id, String issuer, String subject, long ttlMillis) {

        //long timeInMillis = 1000 * 60 * appConfig.getJwtTokenExpiration();
        // user.setAuthToken(
        // jwtTokens.createJWT(
        /// request.getUsername(), appConfig.getName(), "USER_PROFILE_TOKEN", timeInMillis));

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        Key key = Keys.hmacShaKeyFor(config.getJwtToken().getBytes(StandardCharsets.UTF_8));


        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(key,SignatureAlgorithm.HS256);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public Claims parseJWT(String jwt) {

        //We will sign our JWT with our ApiKey secret
        Key key = Keys.hmacShaKeyFor(config.getJwtToken().getBytes(StandardCharsets.UTF_8));

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt).getBody();
        return claims;
    }
}
