package org.example.threads;

public class Task11_ExceptionHandler {
    public static void run() {
        Thread t = new Thread(() -> {
            throw new RuntimeException("Тестовое исключение");
        });
        t.setUncaughtExceptionHandler((thread, ex) -> {
            System.out.println("Поймано исключение в потоке " + thread.getName() + ": " + ex.getMessage());
        });
        t.start();
    }
}