package org.example.threads;

import java.util.concurrent.*;

public class Task7_FutureCallable {
    public static long calculateFactorial(int n) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Long> future = executor.submit(() -> {
            long result = 1;
            for (int i = 2; i <= n; i++) result *= i;
            return result;
        });
        long result = future.get();
        executor.shutdown();
        return result;
    }
}