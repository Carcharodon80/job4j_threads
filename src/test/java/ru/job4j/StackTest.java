package ru.job4j;

import static org.junit.Assert.*;

import org.junit.Test;

public class StackTest {

    @Test
    public void when3PushThen3Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, (int) stack.poll());
        assertEquals(2, (int) stack.poll());
        assertEquals(1, (int) stack.poll());
    }

    @Test
    public void when1PushThen1Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        assertEquals(1, (int) stack.poll());
    }

    @Test
    public void when2PushThen2Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        assertEquals(2, (int) stack.poll());
        assertEquals(1, (int) stack.poll());
    }
}