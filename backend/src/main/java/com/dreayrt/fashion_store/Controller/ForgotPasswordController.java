package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.DTOs.ForgotPasswordRequest;
import com.dreayrt.fashion_store.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotPasswordController {
    public ForgotPasswordController() {
    }
    @Autowired
    private AuthService authService;
    @Autowired
    private HttpSession session;

    @GetMapping("/pages/forgotPassword")
    public String index(Model model) {
        if(!model.containsAttribute("ForgotPasswordRequest")){
            model.addAttribute("ForgotPasswordRequest", new ForgotPasswordRequest());
        }
        return "/pages/forgotPassword";
    }
    @PostMapping("/pages/forgotPassword")
    public String ForgotPassword(@ModelAttribute("ForgotPasswordRequest")  ForgotPasswordRequest forgotPasswordRequest ,
                                 Model model){
        try{
            String normalizedEmail = forgotPasswordRequest.getEmail();
            authService.forgotPassword(forgotPasswordRequest.getEmail());
            session.setAttribute("emailForgot", normalizedEmail);
            model.addAttribute("SuccessMessage","Đã Gửi Mã OTP Đến " +forgotPasswordRequest.getEmail());
            return "/pages/forgotPassword";
        }catch(RuntimeException e){
            model.addAttribute("forgotPasswordError",e.getMessage());
            return "/pages/forgotPassword";
        }

    }
}
