package org.example.threads;

import java.util.concurrent.*;

public class Task5_ExecutorService {
    public static long calculateSum() throws InterruptedException, ExecutionException {
        int[] numbers = new int[1000];
        for (int i = 0; i < numbers.length; i++) numbers[i] = i + 1;

        int chunkSize = 100;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Future<Long>[] futures = new Future[10];

        for (int i = 0; i < 10; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, numbers.length);
            futures[i] = executor.submit(() -> {
                long sum = 0;
                for (int j = start; j < end; j++) sum += numbers[j];
                return sum;
            });
        }

        long totalSum = 0;
        for (Future<Long> future : futures) totalSum += future.get();
        executor.shutdown();
        return totalSum;
    }
}