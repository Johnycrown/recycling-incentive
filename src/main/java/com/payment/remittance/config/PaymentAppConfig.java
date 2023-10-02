package com.payment.remittance.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@ConfigurationProperties(prefix = "configs")
public class PaymentAppConfig {
    @NotBlank
    private String name;
    private String jwtToken;
    @Autowired
    private Environment environment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public long getJwtTokenExpiration() {
        return jwtTokenExpiration;
    }

    public void setJwtTokenExpiration(long jwtTokenExpiration) {
        this.jwtTokenExpiration = jwtTokenExpiration;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private long jwtTokenExpiration;



    public String getProperty(String s)
    {
        return environment.getProperty(s);
    }
}
