package com.example.gradient.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ThreadTaskTest {

    @Test
    void testTaskStartsAndFinishes() throws InterruptedException {
        final boolean[] executed = {false};

        ThreadTask task = new ThreadTask(() -> {
            try {
                Thread.sleep(100);
                executed[0] = true;
            } catch (InterruptedException ignored) {}
        });

        assertFalse(task.isStarted(), "Task should not be started initially");
        assertFalse(task.isFinished(), "Task should not be finished initially");

        task.start();
        task.join();

        assertTrue(executed[0], "Task should have executed");
        assertTrue(task.isStarted(), "Task should be marked as started");
        assertTrue(task.isFinished(), "Task should be marked as finished");
    }

    @Test
    void testOnFinishedCallbackIsCalled() throws InterruptedException {
        final boolean[] called = {false};

        ThreadTask task = new ThreadTask(() -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {}
        });

        task.setOnFinished(() -> called[0] = true);

        task.start();
        task.join();

        assertTrue(called[0], "Callback should be called");
    }

    @Test
    void testInterruptBehavior() throws InterruptedException {
        ThreadTask task = new ThreadTask(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // re-interrupt
            }
        });

        task.start();
        Thread.sleep(30);
        task.interrupt();
        task.join();

        assertTrue(task.isFinished(), "Task should be marked as finished after interrupt");
        assertTrue(task.isInterrupted(), "Thread should be marked as interrupted");
    }

    @Test
    void testThreadProperties() {
        Runnable dummy = () -> {};
        ThreadTask task = new ThreadTask(dummy);

        task.setName("MyThread");
        task.setPriority(Thread.MAX_PRIORITY);

        assertEquals("MyThread", task.getName());
        assertEquals(Thread.MAX_PRIORITY, task.getPriority());
        assertNotNull(task.getTask());
        assertNotNull(task.getId());
    }

    @Test
    void testToStringHasInfo() {
        ThreadTask task = new ThreadTask(() -> {});
        String result = task.toString();

        assertTrue(result.contains("ThreadTask{"));
        assertTrue(result.contains("id='"));
    }
}
