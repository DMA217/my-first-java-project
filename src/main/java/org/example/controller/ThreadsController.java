package org.example.controller;

import org.example.threads.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/threads")
public class ThreadsController {

    @GetMapping("/task1")
    public String task1() throws InterruptedException {
        Task1_ThreadCreation.run();
        return "Задача 1 выполнена (смотри консоль)";
    }

    @GetMapping("/task2")
    public String task2() throws InterruptedException {
        int broken = Task2_RaceCondition.runBroken();
        int fixed = Task2_RaceCondition.runFixed();
        return String.format("Без синхронизации: %d\nС синхронизацией: %d", broken, fixed);
    }

    @GetMapping("/task3")
    public String task3() throws InterruptedException {
        Task3_ProducerConsumer.run();
        return "Задача 3 выполнена (смотри консоль)";
    }

    @GetMapping("/task4")
    public String task4() {
        Task4_Deadlock.run();
        return "Deadlock запущен (программа зависнет)";
    }

    @GetMapping("/task5")
    public String task5() throws Exception {
        long sum = Task5_ExecutorService.calculateSum();
        return "Сумма: " + sum;
    }

    @GetMapping("/task6")
    public String task6() throws InterruptedException {
        Task6_CountDownLatch.run();
        return "Задача 6 выполнена (смотри консоль)";
    }

    @GetMapping("/task7")
    public String task7() throws Exception {
        long result = Task7_FutureCallable.calculateFactorial(10);
        return "Факториал 10: " + result;
    }

    @GetMapping("/task8")
    public String task8() throws InterruptedException {
        Task8_ConcurrentHashMap.run();
        return "Задача 8 выполнена (смотри консоль)";
    }

    @GetMapping("/task9")
    public String task9() {
        Task9_Priorities.run();
        return "Задача 9 выполнена (смотри консоль)";
    }

    @GetMapping("/task10")
    public String task10() throws InterruptedException {
        Task10_Volatile.run();
        return "Задача 10 выполнена (смотри консоль)";
    }

    @GetMapping("/task11")
    public String task11() {
        Task11_ExceptionHandler.run();
        return "Задача 11 выполнена (смотри консоль)";
    }

    @GetMapping("/task12")
    public String task12() throws InterruptedException {
        Task12_SynchronizedCollection.run();
        return "Задача 12 выполнена (смотри консоль)";
    }
}