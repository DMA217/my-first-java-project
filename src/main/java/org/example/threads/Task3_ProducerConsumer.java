package org.example.threads;

import java.util.LinkedList;
import java.util.Queue;

public class Task3_ProducerConsumer {
    private static final Queue<Integer> queue = new LinkedList<>();
    private static final int CAPACITY = 5;

    public static void run() throws InterruptedException {
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) { produce(i); Thread.sleep(200); }
            } catch (InterruptedException e) {}
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) { consume(); Thread.sleep(500); }
            } catch (InterruptedException e) {}
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }

    private static synchronized void produce(int item) throws InterruptedException {
        while (queue.size() == CAPACITY) Task3_ProducerConsumer.class.wait();
        queue.add(item);
        System.out.println("Произведено: " + item + " (в очереди: " + queue.size() + ")");
        Task3_ProducerConsumer.class.notifyAll();
    }

    private static synchronized void consume() throws InterruptedException {
        while (queue.isEmpty()) Task3_ProducerConsumer.class.wait();
        int item = queue.poll();
        System.out.println("Потреблено: " + item + " (в очереди: " + queue.size() + ")");
        Task3_ProducerConsumer.class.notifyAll();
    }
}