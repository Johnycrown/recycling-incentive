package com.payment.remittance.transaction.repository;

import com.payment.remittance.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllById(Long userId);
}
