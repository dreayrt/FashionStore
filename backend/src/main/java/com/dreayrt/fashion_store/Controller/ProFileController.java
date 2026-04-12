package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import org.hibernate.annotations.Parameter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProFileController {
    private final TaiKhoanRepository taiKhoanRepository;

    public ProFileController(TaiKhoanRepository taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }

    @GetMapping("/pages/profile")
    public String profilePage(@RequestParam(value = "username", required = false) String username, Authentication authentication, Model model){
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/pages/login";
        }
        TaiKhoan currentUser = taiKhoanRepository.findByUsername(authentication.getName()).orElse(null);
        if (currentUser == null) {
            return "redirect:/pages/login";
        }
        if (username != null && !currentUser.getUsername().equals(username)) {
            return "redirect:/pages/failed";
        }
        model.addAttribute("user",currentUser);
        return "pages/proFile";
    }
}
