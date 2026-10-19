package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class ConsoleApplication {

    public static void main(String[] args) {
        // Отключаем запуск веб-сервера (Tomcat), так как это консольное приложение
        SpringApplication app = new SpringApplication(ConsoleApplication.class);
        //app.setWebApplicationType(org.springframework.boot.WebApplicationType.NONE);
        app.run(args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ArgumentService argumentService) {
        return args -> {
            System.out.println("Приложение запущено. Введите аргументы через пробел (или напишите 'exit' для выхода):");

            // Мы используем цикл, чтобы программа не закрывалась после первого ввода
            try (Scanner scanner = new Scanner(System.in)) {
                while (scanner.hasNextLine()) {
                    String input = scanner.nextLine();

                    if ("exit".equalsIgnoreCase(input.trim())) {
                        break; // Выход из цикла, если ввели exit
                    }

                    if (input.trim().isEmpty()) continue; // Пропускаем пустые строки

                    String[] inputArgs = input.trim().split("\\s+");
                    String result = argumentService.processArguments(inputArgs);

                    System.out.println("\n--- Результат ---");
                    System.out.println(result);
                    System.out.println("Введите следующие аргументы:");
                }
            }
        };
    }
}