package org.example.threads;

import java.util.concurrent.ConcurrentHashMap;

public class Task8_ConcurrentHashMap {
    private static final ConcurrentHashMap<String, Integer> cache = new ConcurrentHashMap<>();

    public static void run() throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            final int id = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    String key = "key_" + (j % 10);
                    cache.merge(key, 1, Integer::sum);
                }
            });
            threads[i].start();
        }
        for (Thread t : threads) t.join();
        System.out.println("Размер кэша: " + cache.size());
        System.out.println("Значения: " + cache);
    }
}