package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.Service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {
    @Autowired
    private AuthService authService;
    @GetMapping("/pages/logout")
    public String index(HttpSession session, HttpServletResponse response) {
        TaiKhoan username=(TaiKhoan) session.getAttribute("user");
        if(username!=null){
            authService.Logout(username.getUsername());
            session.invalidate();

            clearRememberCookie("REMEMBER_LOGIN", response, true);
        }
        return "redirect:/";
    }

    private void clearRememberCookie(String name, HttpServletResponse response, boolean httpOnly) {
        Cookie cookie=new Cookie(name,null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(httpOnly);
        response.addCookie(cookie); 
    }
}
