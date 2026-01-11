package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.DTOs.LoginRequest;
import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.Service.AuthService;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class LoginController {
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    @Autowired
    private AuthService authService;

    @GetMapping("/pages/login")
    public String Login() {
        return "pages/login";
    }
}
