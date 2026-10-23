package org.example;

import org.example.service.ArgumentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class ConsoleApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ConsoleApplication.class);
        //app.setWebApplicationType(org.springframework.boot.WebApplicationType.NONE);
        app.run(args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ArgumentService argumentService) {
        return args -> {
            System.out.println("Приложение запущено. Введите аргументы через пробел (или напишите 'exit' для выхода):");

            try (Scanner scanner = new Scanner(System.in)) {
                while (scanner.hasNextLine()) {
                    String input = scanner.nextLine();

                    if ("exit".equalsIgnoreCase(input.trim())) {
                        break;
                    }

                    if (input.trim().isEmpty()) continue;

                    String[] inputArgs = input.trim().split("\\s+");
                    String result = argumentService.processArguments(inputArgs);

                    System.out.println("\nРезультат");
                    System.out.println(result);
                    System.out.println("Введите следующие аргументы:");
                }
            }
        };
    }
}