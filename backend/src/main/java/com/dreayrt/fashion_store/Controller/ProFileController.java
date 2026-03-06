package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import jakarta.servlet.http.HttpSession;
import org.hibernate.annotations.Parameter;
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
    public String profilePage(@RequestParam("username") String username, HttpSession session, Model model){
        TaiKhoan sessionUser = (TaiKhoan) session.getAttribute("user");
        if(sessionUser == null || !sessionUser.getUsername().equals(username)){
            return "pages/login";
        }
        model.addAttribute("user",sessionUser);
        return "pages/proFile";
    }
}
