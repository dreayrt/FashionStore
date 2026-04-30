package com.dreayrt.fashion_store.Config;

import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CurrentUserAdvice {
    private final TaiKhoanRepository taiKhoanRepository;

    @Value("${app.r2.public-url}")
    private String r2PublicUrl;

    public CurrentUserAdvice(TaiKhoanRepository taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }

    @ModelAttribute("authUser")
    public TaiKhoan authUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetails)) {
            return null;
        }

        return taiKhoanRepository.findByUsername(authentication.getName()).orElse(null);
    }

    @ModelAttribute("r2PublicUrl")
    public String r2PublicUrl() {
        return r2PublicUrl;
    }
}
