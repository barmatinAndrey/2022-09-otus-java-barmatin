package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String startPage() {
        return "index";
    }

    @GetMapping("/clients")
    public String clientPage() {
        return "clients";
    }

}
