package org.example.service;

import org.springframework.stereotype.Service;

@Service
public class ApiService {

    public String getGreeting(String name) {
        return "Привет, " + name + ". Это ответ из сервиса.";
    }

    public String processText(String text) {
        return new StringBuilder(text).reverse().toString();
    }
}