package org.example.threads;

public class Task1_ThreadCreation {
    public static void run() throws InterruptedException {
        Thread t1 = new Thread(() -> printNumbers("Поток-1"));
        Thread t2 = new Thread(() -> printNumbers("Поток-2"));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    private static void printNumbers(String name) {
        for (int i = 1; i <= 5; i++) {
            System.out.println(name + ": " + i);
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }
    }
}