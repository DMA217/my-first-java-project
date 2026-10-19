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
        app.setWebApplicationType(org.springframework.boot.WebApplicationType.NONE);
        app.run(args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ArgumentService argumentService) {
        return args -> {
            // Этот блок выполнится сразу после запуска Spring
            System.out.println("Приложение запущено. Введите аргументы через пробел:");

            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                // Разбиваем введенную строку на массив аргументов по пробелам
                String[] inputArgs = input.trim().split("\\s+");

                // Передаем аргументы в наш сервис
                String result = argumentService.processArguments(inputArgs);

                // Выводим результат обратно в консоль
                System.out.println("\n--- Результат ---");
                System.out.println(result);
            }
            scanner.close();
        };
    }
}