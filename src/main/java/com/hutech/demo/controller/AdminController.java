package com.hutech.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String admin() {
        return "admin/page-index-1";
    }

    @GetMapping("/admin/page-products-list")
    public String product() {
        return "admin/page-products-list";
    }
}