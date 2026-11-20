package org.example.threads;

public class Task9_Priorities {
    public static void run() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) System.out.println("Поток 1 (MIN): " + i);
        });
        t1.setPriority(Thread.MIN_PRIORITY);

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) System.out.println("Поток 2 (MAX): " + i);
        });
        t2.setPriority(Thread.MAX_PRIORITY);

        t1.start();
        t2.start();
    }
}