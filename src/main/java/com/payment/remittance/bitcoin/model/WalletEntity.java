package com.payment.remittance.bitcoin.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(name = "wallet_data")
    private byte[] walletData;
    private Long userId;
    @Column(precision = 20, scale = 9)
    private BigDecimal balance;

}
