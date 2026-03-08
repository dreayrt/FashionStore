package com.dreayrt.fashion_store.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class outstandingController {
    @GetMapping("/pages/outstanding")
    public String OutStanding(){
        return "pages/outstanding";
    }
}
