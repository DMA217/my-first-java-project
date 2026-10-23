package org.example.controller;

import org.example.service.ApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "Гость") String name) {
        return apiService.getGreeting(name);
    }

    @GetMapping("/reverse")
    public String reverse(@RequestParam String text) {
        return apiService.processText(text);
    }
}