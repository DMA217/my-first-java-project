package org.example.service;

import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.Queue;

@Service
public class ProducerConsumerService {

    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacity = 5;

    public void runProducerConsumer() throws InterruptedException {
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    produce(i);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    consume();
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }

    private synchronized void produce(int item) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }
        queue.add(item);
        System.out.println("Произведено: " + item + ". В очереди: " + queue.size());
        notifyAll();
    }

    private synchronized void consume() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        int item = queue.poll();
        System.out.println("Потреблено: " + item + ". В очереди: " + queue.size());
        notifyAll();
    }
}