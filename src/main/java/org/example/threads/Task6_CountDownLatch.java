package org.example.threads;

import java.util.concurrent.CountDownLatch;

public class Task6_CountDownLatch {
    public static void run() throws InterruptedException {
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(5);

        for (int i = 1; i <= 5; i++) {
            final int id = i;
            new Thread(() -> {
                try {
                    startLatch.await();
                    System.out.println("Гонщик " + id + " стартовал");
                    Thread.sleep(500 + (id * 100));
                    System.out.println("Гонщик " + id + " финишировал");
                    finishLatch.countDown();
                } catch (InterruptedException e) {}
            }).start();
        }

        Thread.sleep(1000);
        System.out.println("Старт!");
        startLatch.countDown();
        finishLatch.await();
        System.out.println("Все финишировали!");
    }
}