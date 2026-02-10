package com.dreayrt.fashion_store.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FailedRoleController {
    @GetMapping("/pages/failed")
    public String failedPage() {
        return "pages/failed";
    }
}
