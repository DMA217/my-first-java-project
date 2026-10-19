package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestController
public class MyController {

    // Внедряем наш сервис через Spring
    private final TextService textService;

    @Autowired
    public MyController(TextService textService) {
        this.textService = textService;
    }

    // 1. Удаление гласных
    @GetMapping("/remove-vowels")
    public ResponseEntity<String> removeVowels(@RequestParam(value = "param", defaultValue = "") String param) {
        if (param.trim().isEmpty()) {
            // Возвращаем статус 400 и текст ошибки
            return ResponseEntity.badRequest().body("Ошибка: параметр 'param' не может быть пустым.");
        }
        return ResponseEntity.ok(textService.removeVowels(param));
    }

    @GetMapping("/count-consonants")
    public ResponseEntity<?> countConsonants(@RequestParam(value = "param", defaultValue = "") String param) {
        if (param.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Ошибка: параметр 'param' не может быть пустым.");
        }
        int count = textService.countConsonants(param);
        return ResponseEntity.ok(count);
    }
}