package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.Model.Entities.OtpLog;
import com.dreayrt.fashion_store.repository.OtpLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private OtpLogRepository otpLogRepository;

    public void SendEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Mã OTP Khôi Phục Mật Khẩu");
        message.setText("Mã OTP của bạn là: " + otp + "\n\n" +
                "OTP có hiệu lực trong 5 phút.\n" +
                "Nếu bạn không yêu cầu, vui lòng bỏ qua email này."
        );
        mailSender.send(message);
    }
    // Check OTP còn hạn cho email
    public void verifyOtp(String email, String otpInput) {
        Date now = new Date();
        OtpLog otpLog = otpLogRepository
                .findFirstByEmailAndOtpAndExpiratedatAfterOrderByCreateatDesc(email, otpInput, now)
                .orElseThrow(() -> new RuntimeException("OTP Không Tồn Tại Hoặc Đã Hêt Hạn"));
        otpLogRepository.save(otpLog);
    }

}
