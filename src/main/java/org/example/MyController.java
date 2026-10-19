package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/remove-vowels")
    public String removeVowels(@RequestParam(value = "param", defaultValue = "") String param) {
        if (param == null) {
            return "";
        }
        // Удаляем гласные (a, e, i, o, u) без учета регистра
        String result = param.replaceAll("(?i)[aeiou]", "");
        return result;
    }
}