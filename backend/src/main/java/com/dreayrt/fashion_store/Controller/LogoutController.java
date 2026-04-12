package com.dreayrt.fashion_store.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {
    @GetMapping("/pages/logout")
    public String index(HttpServletResponse response) {
        clearCookie("ACCESS_TOKEN", response);
        clearCookie("REFRESH_TOKEN", response);
        clearCookie("REMEMBER_LOGIN", response);
        return "redirect:/";
    }

    private void clearCookie(String name, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        response.addCookie(cookie);
    }
}
