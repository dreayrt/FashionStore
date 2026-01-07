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

import java.net.http.HttpResponse;


@Controller
public class LoginController {
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    @Autowired
    private AuthService authService;

    @GetMapping("/pages/login")
    public String Login(HttpServletRequest request, HttpSession session, Model model) {
        if (session.getAttribute("user") != null) {
            return "redirect:/";
        }
        //auto login with cookie
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("REMEMBER_LOGIN".equals(c.getName())) {
                    String username = c.getValue().trim();
                    TaiKhoan taiKhoan = taiKhoanRepository.findByUsername(username);
                    if (taiKhoan != null) {
                        session.setAttribute("user", taiKhoan);
                        return "redirect:/";
                    }
                }
                if ("REMEMBER_USERNAME".equals(c.getName())) {
                    model.addAttribute("savedUsername", c.getValue());
                }
            }
        }
        model.addAttribute("loginRequest", new LoginRequest());
        return "pages/login";
    }

    @PostMapping("/pages/login")
    public String login(@Valid @ModelAttribute LoginRequest loginRequest, BindingResult bindingResult, Model model, SessionStatus sessionStatus, HttpSession httpSession, HttpServletResponse response) {
        if(bindingResult.hasErrors()){
            return "pages/login";
        }
        try{
            TaiKhoan user= authService.Login(loginRequest.getUsername(), loginRequest.getPassword());
            httpSession.setAttribute("user", user);

            //cookie remember account
            if(Boolean.TRUE.equals(loginRequest.getRemember())){
                String username = user.getUsername().trim();

                Cookie loginCookie = new Cookie("REMEMBER_LOGIN", username);
                loginCookie.setMaxAge(7 * 24 * 60 * 60);
                loginCookie.setPath("/");
                loginCookie.setHttpOnly(true);
                response.addCookie(loginCookie);

                // cookie nhớ username
                Cookie userCookie = new Cookie("REMEMBER_USERNAME", username);
                userCookie.setMaxAge(30 * 24 * 60 * 60);
                userCookie.setPath("/");
                response.addCookie(userCookie);

            }
            return "redirect:/";
        }catch(RuntimeException e){
            model.addAttribute("loginError", e.getMessage());
            return "pages/login";
        }
    }
}
