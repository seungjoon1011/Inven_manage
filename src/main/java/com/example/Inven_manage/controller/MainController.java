package com.example.Inven_manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String hello() {
        return "main"; // "hello.html" 템플릿을 렌더링하여 반환
    }
}
