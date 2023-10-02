package com.payment.remittance.usermanagement.repository;


import com.payment.remittance.usermanagement.model.ClientAccesCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientAccessCredRepository extends JpaRepository<ClientAccesCredentials, Long> {
    ClientAccesCredentials   findByClientId(String clientID );
}
