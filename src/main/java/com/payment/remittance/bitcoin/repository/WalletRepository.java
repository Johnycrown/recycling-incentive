package com.payment.remittance.bitcoin.repository;

import com.payment.remittance.bitcoin.model.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {


   Optional<WalletEntity> findByUserId(long userId);
}
