package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {


    @GetMapping("/admin")
    String admin() {
        return "admin";
    }

    @GetMapping("/user")
    String user() {
        return "user";
    }


}