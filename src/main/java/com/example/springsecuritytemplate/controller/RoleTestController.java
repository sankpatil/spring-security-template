package com.example.springsecuritytemplate.controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoleTestController {

    @GetMapping("/user/dashboard")
    public String userDashboard() {
        return "Welcome to User Dashboard";
    }

    @GetMapping("/seller/dashboard")
    public String sellerDashboard() {
        return "Welcome to Seller Dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "Welcome to Admin Dashboard";
    }
}
