package com.payment.remittance.usermanagement.repository;


import com.payment.remittance.usermanagement.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    boolean existsByEmail(String Email);
    Optional<AppUser> findByEmail(String email);
}
