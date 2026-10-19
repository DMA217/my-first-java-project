package org.example;

import org.springframework.stereotype.Service;

@Service
public class ArgumentService {

    /**
     * Обрабатывает массив аргументов и возвращает отформатированную строку.
     * В будущем здесь может быть сложная логика: сохранение в БД, фильтрация и т.д.
     */
    public String processArguments(String[] args) {
        if (args == null || args.length == 0) {
            return "Аргументы не переданы.";
        }

        StringBuilder result = new StringBuilder();
        result.append("Получено аргументов: ").append(args.length).append("\n");

        for (int i = 0; i < args.length; i++) {
            result.append(String.format("Аргумент %d: %s%n", i + 1, args[i]));
        }

        return result.toString();
    }
}