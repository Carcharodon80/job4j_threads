package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    private final int maxSize;

    public SimpleBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * проверяет очередь, если заполнена - в спячку,
     * если нет - добавить в очередь и оповестить 1 другую нить
     */
    public synchronized void offer(T value) {
        while (queue.size() == maxSize) {
            try {
                System.out.println("Очередь заполнена, не могу добавить. Ждем.");
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.offer(value);
        System.out.println("Добавлен в очередь: " + value);
        this.notify();
    }

    /**
     * проверяет очередь, если заполнена - в спячку,
     * если нет - взять из очереди и оповестить 1 другую нить
     */
    public synchronized T poll() {
        T rsl;
        while (queue.size() == 0) {
            try {
                System.out.println("Очередь пуста, не могу получить. Ждем.");

                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        rsl = queue.poll();
        System.out.println("Удален из очереди: " + rsl);
        this.notify();
        return rsl;
    }

    public synchronized boolean isEmpty() {
        return queue.size() == 0;
    }

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        queue.offer(i);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        queue.poll();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}