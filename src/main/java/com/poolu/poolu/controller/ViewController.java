package com.poolu.poolu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/register-driver")
    public String registerDriverPage() {
        return "driver";
    }

    @GetMapping("/register-pooler")
    public String registerPoolerPage() {
        return "pooler";
    }

    @GetMapping("/create-pool")
    public String createPoolPage() {
        return "create-pool";
    }

    @GetMapping("/pools")
    public String poolsPage() {
        return "pools";
    }

    @GetMapping("/join-pool")
    public String joinPoolPage() {
        return "pools";
    }

    @GetMapping("/add-review")
    public String addReviewPage() {
        return "review";
    }

    @GetMapping("/pricing")
    public String pricingPage() {
        return "pricing";
    }

    @GetMapping("/karma")
    public String karmaPage() {
        return "karma";
    }
}
