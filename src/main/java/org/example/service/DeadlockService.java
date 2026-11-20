package org.example.service;

import org.springframework.stereotype.Service;

@Service
public class DeadlockService {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void runDeadlock() {
        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Поток 1: захватил lock1");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                System.out.println("Поток 1: ждет lock2");
                synchronized (lock2) {
                    System.out.println("Поток 1: захватил lock2");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Поток 2: захватил lock2");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                System.out.println("Поток 2: ждет lock1");
                synchronized (lock1) {
                    System.out.println("Поток 2: захватил lock1");
                }
            }
        });

        t1.start();
        t2.start();
    }
}