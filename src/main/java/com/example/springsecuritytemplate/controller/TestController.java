package com.example.springsecuritytemplate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/public/test")
    public String publicEndpoint() {
        return "Public endpoint accessed successfully!";
    }

    @GetMapping("/user/test")
    public String userEndpoint() {
        return "User endpoint accessed successfully!";
    }

    @GetMapping("/admin/test")
    public String adminEndpoint() {
        return "Admin endpoint accessed successfully!";
    }

    @GetMapping("/seller/test")
    public String sellerEndpoint() {
        return "Seller endpoint accessed successfully!";
    }
}