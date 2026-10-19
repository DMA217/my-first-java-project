package org.example;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class TextService {

    // 1. Удаление гласных
    public String removeVowels(String input) {
        if (input == null) return "";
        return input.replaceAll("(?i)[aeiouаеёиоуыэюя]", "");
    }

    // 2. Подсчет согласных (английские и русские)
    public int countConsonants(String input) {
        if (input == null) return 0;
        String consonants = "bcdfghjklmnpqrstvwxyzбвгджзйклмнпрстфхцчшщ";
        int count = 0;
        for (char c : input.toLowerCase().toCharArray()) {
            if (consonants.indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }

    // 3. Карта букв (буква -> количество)
    public Map<Character, Integer> getLetterCounts(String input) {
        Map<Character, Integer> counts = new HashMap<>();
        if (input == null) return counts;

        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                char lowerC = Character.toLowerCase(c);
                counts.put(lowerC, counts.getOrDefault(lowerC, 0) + 1);
            }
        }
        return counts;
    }
}