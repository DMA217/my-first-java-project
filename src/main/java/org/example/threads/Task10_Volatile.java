package org.example.threads;

public class Task10_Volatile {
    private static volatile boolean flag = false;

    public static void run() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (!flag) {}
            System.out.println("Поток 1: увидел flag = true");
        });

        Thread t2 = new Thread(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            flag = true;
            System.out.println("Поток 2: установил flag = true");
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}