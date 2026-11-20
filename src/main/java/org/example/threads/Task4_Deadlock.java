package org.example.threads;

public class Task4_Deadlock {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void run() {
        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Поток 1: захватил lock1");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                synchronized (lock2) { System.out.println("Поток 1: захватил lock2"); }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Поток 2: захватил lock2");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                synchronized (lock1) { System.out.println("Поток 2: захватил lock1"); }
            }
        });

        t1.start();
        t2.start();
    }
}