package com.example.Ecommerce_SellPhone.controller;

import com.example.Ecommerce_SellPhone.models.EmailCodeRequest;
import com.example.Ecommerce_SellPhone.service.Email.EmailService;
import com.example.Ecommerce_SellPhone.service.FeaturedProduct.FeaturedProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/verify-code")
    public ResponseEntity<?> verify(@RequestBody EmailCodeRequest request) {
        boolean valid = emailService.verifyCode(request.getEmail(), request.getCode());
        return valid
                ? ResponseEntity.ok("Code verified successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired code");
    }
    @PostMapping("/send-code")
    public ResponseEntity<String> sendVerificationCode(@RequestBody EmailCodeRequest request) {
        try {
            emailService.sendVerificationCode(request.getEmail());
            return ResponseEntity.ok("Verification code sent to " + request.getEmail());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send email: " + e.getMessage());
        }
    }

}
