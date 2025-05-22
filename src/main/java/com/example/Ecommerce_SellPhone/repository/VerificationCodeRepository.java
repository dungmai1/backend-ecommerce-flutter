package com.example.Ecommerce_SellPhone.repository;

import com.example.Ecommerce_SellPhone.models.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode,Long> {
    Optional<VerificationCode> findByEmailAndCode(String email, String code);
}
