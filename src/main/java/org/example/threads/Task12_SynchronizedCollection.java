package org.example.threads;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class Task12_SynchronizedCollection {
    private static final List<String> list = Collections.synchronizedList(new ArrayList<>());

    public static void run() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) list.add("Thread-1: " + i);
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) list.add("Thread-2: " + i);
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Размер коллекции: " + list.size());
        list.forEach(System.out::println);
    }
}