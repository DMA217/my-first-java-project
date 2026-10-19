package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public String removeVowels(@RequestParam(value = "param", defaultValue = "") String param) {
        return textService.removeVowels(param);
    }

    // 2. Подсчет согласных
    @GetMapping("/count-consonants")
    public int countConsonants(@RequestParam(value = "param", defaultValue = "") String param) {
        return textService.countConsonants(param);
    }

    // 3. Карта букв (вернет JSON вида {"h": 1, "e": 1, "l": 3, "o": 1})
    @GetMapping("/letter-map")
    public Map<Character, Integer> getLetterMap(@RequestParam(value = "param", defaultValue = "") String param) {
        return textService.getLetterCounts(param);
    }
}