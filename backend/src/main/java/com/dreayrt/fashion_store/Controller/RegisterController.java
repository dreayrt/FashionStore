package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.DTOs.RegisterRequest;
import com.dreayrt.fashion_store.Service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    private final AuthService authService;

    public RegisterController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/pages/register")
    public String register(){
        return "pages/register";
    }
    @PostMapping("/pages/register")
    public String register(@Valid @ModelAttribute("RegisterRequest") RegisterRequest registerRequest,
                           BindingResult bindingResult,
                           HttpSession session,
                           Model model){
        if(bindingResult.hasErrors()){
            return "pages/register";
        }
        try{
            authService.Register(registerRequest);
            model.addAttribute("RegisterSuccess",true);
            session.setAttribute("RegisterUsername",registerRequest.getUsername());
            return "redirect:/pages/chooseAvatar";
        }catch(RuntimeException e) {
            model.addAttribute("RegisterError", e.getMessage());
            return "pages/register";
        }
    }

}
