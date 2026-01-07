package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.DTOs.ForgotPasswordRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ForgotPasswordController {
    @GetMapping("/pages/forgotPassword")
    public String index(Model model) {
        if(!model.containsAttribute("ForgotPasswordRequest")){
            model.addAttribute("ForgotPasswordRequest", new ForgotPasswordRequest());
        }
        return "/pages/forgotPassword";
    }

   
}
