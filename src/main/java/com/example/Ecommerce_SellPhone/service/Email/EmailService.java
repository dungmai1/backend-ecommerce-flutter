package com.example.Ecommerce_SellPhone.service.Email;

import com.example.Ecommerce_SellPhone.models.VerificationCode;
import com.example.Ecommerce_SellPhone.repository.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VerificationCodeRepository codeRepository;

    public void sendVerificationCode(String toEmail) {
        String code = generateRandomCode();

        VerificationCode verification = new VerificationCode();
        verification.setEmail(toEmail);
        verification.setCode(code);
        verification.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        codeRepository.save(verification);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Verification Code");
        message.setText("Your verification code is: " + code);
        mailSender.send(message);
    }

    public boolean verifyCode(String email, String code) {
        Optional<VerificationCode> record = codeRepository.findByEmailAndCode(email, code);
        return record.isPresent() && record.get().getExpiresAt().isAfter(LocalDateTime.now());
    }

    private String generateRandomCode() {
        return String.valueOf((int)(Math.random() * 900000) + 100000); // 6-digit code
    }
}
