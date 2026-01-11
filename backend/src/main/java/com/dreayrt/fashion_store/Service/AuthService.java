package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.DTOs.RegisterRequest;
import com.dreayrt.fashion_store.Util.HashUtil;
import com.dreayrt.fashion_store.Model.Entities.OtpLog;
import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.Util.OTPUtil;
import com.dreayrt.fashion_store.repository.OtpLogRepository;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AuthService {
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private HttpSession session;
    @Autowired
    private OtpLogRepository otpLogRepository;

    @Transactional
    public TaiKhoan Login(String username, String password) {
        String normalizedUsername = username == null ? "" : username.trim();
        TaiKhoan taiKhoan = taiKhoanRepository.findByUsername(normalizedUsername)
                .filter(t -> t.getUsername() != null && t.getUsername().trim().equals(normalizedUsername))
                .orElseThrow(() -> new RuntimeException("Tài Khoản không tồn tại"));
        if("Locked".equals(taiKhoan.getTrangThai())){
            throw new RuntimeException("Tài Khoản Đang Bị Khóa");
        }
        String passWordHash= HashUtil.sha256(password);

        if(!passWordHash.equals(taiKhoan.getPassword())){
            throw new RuntimeException("Sai Mật Khẩu");
        }
        taiKhoan.setTrangThai("Online");
        taiKhoanRepository.save(taiKhoan);
        return taiKhoan;
    }
    @Transactional
    public void Logout(String username){
        String normalizedUsername = username == null ? "" : username.trim();
        taiKhoanRepository.findByUsername(normalizedUsername)
                .filter(t -> t.getUsername() != null && t.getUsername().trim().equals(normalizedUsername))
                .filter(t -> !"Locked".equals(t.getTrangThai()))
                .ifPresent(t -> {
                    t.setTrangThai("Offline");
                    taiKhoanRepository.save(t);
                });
    }

    @Transactional
    public void Register(RegisterRequest registerRequest){
        if(taiKhoanRepository.existsByUsername(registerRequest.getUsername())){
            throw new RuntimeException("Tài Khoản Đã Tồn Tại");
        }
        if(!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())){
            throw new RuntimeException("Mật Khẩu Không Khớp");
        }
        if(taiKhoanRepository.existsByEmail(registerRequest.getEmail())){
            throw new RuntimeException("Email Này Đã Được Sử Dụng");
        }
        if(taiKhoanRepository.existsByPhone(registerRequest.getPhone())){
            throw new RuntimeException("Số Điện Thoại Này Đã Được Đăng Ký");
        }
        String passwordHash=HashUtil.sha256(registerRequest.getPassword());
        TaiKhoan taiKhoan =new TaiKhoan();
        taiKhoan.setUsername(registerRequest.getUsername().trim());
        taiKhoan.setPassword(passwordHash);
        taiKhoan.setTrangThai("Offline");
        taiKhoan.setEmail(registerRequest.getEmail());
        taiKhoan.setDiaChi(registerRequest.getAddress());
        taiKhoan.setPhone(registerRequest.getPhone());

        String email=registerRequest.getEmail();
        String emailPrefix =email.split("@")[0].toLowerCase();
        if(emailPrefix.startsWith("admin")){
            taiKhoan.setVaiTro("ADMIN");
        }else if(emailPrefix.startsWith("staff")){
            taiKhoan.setVaiTro("STAFF");
        }else{
            taiKhoan.setVaiTro("USER");
        }
        taiKhoanRepository.save(taiKhoan);
    }

    public void forgotPassword(String email){
        String normalizedEmail = email == null ? "" : email.trim();
        TaiKhoan taiKhoan = taiKhoanRepository.findByEmail(normalizedEmail)
                .filter(t -> t.getEmail() != null && t.getEmail().equals(normalizedEmail))
                .orElseThrow(() -> new RuntimeException("Email Này Không Tồn Tại"));
        String otp= OTPUtil.generateOtp();
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + 5 * 60 * 1000);
        OtpLog otpLog = new OtpLog();
        otpLog.setEmail(normalizedEmail);
        otpLog.setOtp(otp);
        otpLog.setCreateat(now);
        otpLog.setExpiratedat(expiredAt);
        otpLog.setTaiKhoan(taiKhoan);
        otpLogRepository.save(otpLog);

        emailService.SendEmail(email, otp);
    }
    public void resetPassword(String email,String otpInput,String newPassword){
        emailService.verifyOtp(email, otpInput);
        TaiKhoan taiKhoan =taiKhoanRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("Email Không Tồn Tại"));
        taiKhoan.setPassword(HashUtil.sha256(newPassword));
        taiKhoanRepository.save(taiKhoan);
        otpLogRepository.findFirstByEmailAndOtpAndExpiratedatAfterOrderByCreateatDesc(email, otpInput, new Date())
                .ifPresent(o -> { o.setExpiratedat(new Date()); otpLogRepository.save(o); });
    }


}
