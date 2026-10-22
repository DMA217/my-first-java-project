package org.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MyController {

    private final TextService textService;
    private final RequestLogRepository logRepository;

    public MyController(TextService textService, RequestLogRepository logRepository) {
        this.textService = textService;
        this.logRepository = logRepository;
    }

    @GetMapping("/remove-vowels")
    public ResponseEntity<String> removeVowels(@RequestParam(value = "param", defaultValue = "") String param) {
        if (param.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Ошибка: параметр 'param' не может быть пустым.");
        }

        logRepository.save(new RequestLog("remove-vowels", param));

        return ResponseEntity.ok(textService.removeVowels(param));
    }

    @GetMapping("/count-consonants")
    public ResponseEntity<?> countConsonants(@RequestParam(value = "param", defaultValue = "") String param) {
        if (param.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Ошибка: параметр 'param' не может быть пустым.");
        }

        logRepository.save(new RequestLog("count-consonants", param));

        return ResponseEntity.ok(textService.countConsonants(param));
    }

    @GetMapping("/letter-map")
    public ResponseEntity<?> getLetterMap(@RequestParam(value = "param", defaultValue = "") String param) {
        if (param.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Ошибка: параметр 'param' не может быть пустым.");
        }

        logRepository.save(new RequestLog("letter-map", param));

        return ResponseEntity.ok(textService.getLetterCounts(param));
    }
}