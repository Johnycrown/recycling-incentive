package com.payment.remittance.usermanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
public class ClientAccesCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientId;
    private String sourceCode;
    private String secretKey;
    private String publicKey;
    private String entityCodeAllowed;
    private String apiKey;
    private String callbackUrl;
    private String notificationUrl;
    private String countryCode;
    private String parentCode;
    @CreationTimestamp
    private Date createdDate;
    @UpdateTimestamp
    private Date modifiedDate;
    private String status;
    private String entityCode;
    private String bankAccount;
    private String bankCode;
    private Date tokenExpiryDate;
    private String ipAddress;
    private String token;
}
