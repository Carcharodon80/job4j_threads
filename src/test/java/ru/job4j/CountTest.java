package ru.job4j;

import org.junit.Test;

import static org.junit.Assert.*;

public class CountTest {
    private static class ThreadCount extends Thread {
        private final Count count;

        public ThreadCount(Count count) {
            this.count = count;
        }

        @Override
        public void run() {
            this.count.increment();
        }
    }

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        final Count count = new Count();
        Thread first = new ThreadCount(count);
        Thread second = new ThreadCount(count);
        first.start();
        second.start();
        first.join();
        second.join();
        assertEquals(count.get(), 2);
    }
}