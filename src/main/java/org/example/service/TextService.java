package org.example.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
public class TextService {

    public String removeVowels(String input) {
        if (input == null) return "";
        return input.replaceAll("(?iu)[aeiouyаеёиоуыэюя\\s]", "");
    }

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

    public Map<Character, Integer> getLetterCounts(String input) {
        Map<Character, Integer> counts = new TreeMap<>();
        if (input == null) return counts;

        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                char lowerC = Character.toLowerCase(c);
                counts.put(lowerC, counts.getOrDefault(lowerC, 0) + 1);
            }
        }
        return counts;
    }

    public Map<Character, Long> countLettersWithStream(String input) {
        Map<Character, Long> frequencyMap = new HashMap<>();

        if (input == null || input.isEmpty()) {
            return frequencyMap;
        }

        input.chars()
                .mapToObj(c -> (char) c)
                .filter(Character::isLetter)
                .map(Character::toLowerCase)
                .forEach(c -> frequencyMap.merge(c, 1L, Long::sum));

        return frequencyMap;
    }
}