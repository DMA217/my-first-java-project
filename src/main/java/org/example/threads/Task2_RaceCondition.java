package org.example.threads;

public class Task2_RaceCondition {
    private static int counter = 0;

    public static int runBroken() throws InterruptedException {
        counter = 0;
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) counter++;
            });
            threads[i].start();
        }
        for (Thread t : threads) t.join();
        return counter;
    }

    public static int runFixed() throws InterruptedException {
        counter = 0;
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) increment();
            });
            threads[i].start();
        }
        for (Thread t : threads) t.join();
        return counter;
    }

    private static synchronized void increment() { counter++; }
}