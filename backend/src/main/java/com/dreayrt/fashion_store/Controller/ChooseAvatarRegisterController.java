package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.DTOs.RegisterRequest;
import com.dreayrt.fashion_store.Service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ChooseAvatarRegisterController {
    @Autowired
    private AuthService authService;

    @GetMapping("/pages/chooseAvatar")
    public String getChooseAvatar() {
        return "pages/chooseAvatar";
    }

    @PostMapping("/pages/ChooseAvatar")
    public String ChooseAvatar(@RequestParam(required = false) String avatar, @RequestParam(required = false) MultipartFile customAvatar, HttpSession session) {
        Object registerUsername = session.getAttribute("RegisterUsername");
        if (registerUsername == null) {
            return "redirect:/pages/register";
        }
        String username = registerUsername.toString();
        if (customAvatar != null && !customAvatar.isEmpty()) {
            String fileName = customAvatar.getOriginalFilename();
            authService.registerChooseAvatar(username, "/Avatar/" + fileName);
        } else if (avatar != null && !avatar.isBlank()) {
            authService.registerChooseAvatar(username, avatar);
        } else {
            return "redirect:/pages/chooseAvatar";
        }
        return "redirect:/pages/login";
    }
}
