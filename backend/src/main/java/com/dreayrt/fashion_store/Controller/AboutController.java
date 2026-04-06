package com.dreayrt.fashion_store.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {
    @GetMapping("/pages/About")
    public String about(){
        return "pages/About";
    }
}
