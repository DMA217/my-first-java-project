package org.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.Map;

import java.util.Map;

@RestController
public class MyController {

    private final TextService textService;
    private final RequestLogRepository logRepository;
    private final ExceptionService exceptionService;

    public MyController(TextService textService, RequestLogRepository logRepository, ExceptionService exceptionService) {
        this.textService = textService;
        this.logRepository = logRepository;
        this.exceptionService = exceptionService;
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

    @GetMapping("/exception/checked")
    public String triggerChecked() {
        try {
            exceptionService.checkCheckedExceptionRequiredThrows();
        } catch (IOException e) {
            return "Поймали Checked Exception: " + e.getMessage();
        }
        return "Ошибка не выброшена";
    }

    @GetMapping("/exception/unchecked")
    public String triggerUnchecked() {
        exceptionService.checkUncheckedException();
        return "Ошибка не выброшена";
    }

    @GetMapping("/exception/log")
    public String triggerLog(@RequestParam(value = "text", defaultValue = "test") String text) {
        return exceptionService.logSample(text);
    }
}