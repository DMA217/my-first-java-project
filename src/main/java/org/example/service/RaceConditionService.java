package org.example.service;

import org.springframework.stereotype.Service;

@Service
public class RaceConditionService {

    private int counter = 0;

    public int runRaceCondition() throws InterruptedException {
        counter = 0;
        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter++;
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        return counter;
    }

    public synchronized int runRaceConditionFixed() throws InterruptedException {
        counter = 0;
        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    increment();
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        return counter;
    }

    private synchronized void increment() {
        counter++;
    }
}