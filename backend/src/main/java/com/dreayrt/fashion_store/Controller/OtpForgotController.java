package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.DTOs.OtpForgotRequest;
import com.dreayrt.fashion_store.Service.AuthService;
import com.dreayrt.fashion_store.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class OtpForgotController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private AuthService authService;
    @Autowired
    private HttpSession session;

    @GetMapping("pages/OtpForgot")
    public String OtpForgot(Model model){
        if(!model.containsAttribute("OtpForgotRequest")){
            OtpForgotRequest req = new OtpForgotRequest();
            Object emailForgot = session.getAttribute("emailForgot");
            if(emailForgot != null){
                req.setEmail(emailForgot.toString());
            }
            model.addAttribute("OtpForgotRequest", req);
        }
        return "/pages/OtpForgot";
    }
    @PostMapping("pages/OtpForgot")
    public String OtpForgot(@Valid @ModelAttribute OtpForgotRequest otpForgotRequest,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            return "pages/OtpForgot";
        }
        String email = otpForgotRequest.getEmail();
        String otpInput = otpForgotRequest.getOtp();
        String newPassword = otpForgotRequest.getPassword();
        String confirm = otpForgotRequest.getComfirmPassword();

        if (email == null || otpInput == null || newPassword == null || confirm == null
                || email.isBlank() || otpInput.isBlank() || newPassword.isBlank() || confirm.isBlank()) {
            model.addAttribute("otpFailed", "Thiếu thông tin email/OTP/mật khẩu");
            return "pages/OtpForgot";
        }
        if (!newPassword.equals(confirm)) {
            model.addAttribute("otpFailed", "Mật khẩu xác nhận không khớp");
            return "pages/OtpForgot";
        }
        try {
            emailService.verifyOtp(email, otpInput);
            authService.resetPassword(email, otpInput, newPassword);
            model.addAttribute("successMessage", "Đổi mật khẩu thành công, vui lòng đăng nhập lại");
            return "pages/OtpForgot";
        } catch (RuntimeException e) {
            model.addAttribute("otpFailed", e.getMessage());
            return "pages/OtpForgot";
        }
    }

}
