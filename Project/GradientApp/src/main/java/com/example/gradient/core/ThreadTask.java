package com.example.gradient.core;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadTask {
    private final Runnable task;
    private final Thread thread;
    private final String id;
    private final AtomicBoolean started = new AtomicBoolean(false);
    private final AtomicBoolean finished = new AtomicBoolean(false);
    private volatile Runnable onFinishedCallback;

    public ThreadTask(Runnable task) {
        this.task = wrapTask(task);
        this.thread = new Thread(this.task);
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Wraps the original task in a new Runnable that sets the started and finished flags.
     *
     * @param originalTask The original task to be executed.
     * @return A new Runnable that wraps the original task.
     */
    private Runnable wrapTask(Runnable originalTask) {
        return () -> {
            started.set(true);
            try {
                originalTask.run();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                finished.set(true);
                if (onFinishedCallback != null) {
                    onFinishedCallback.run();
                }
            }
        };
    }

    public void start() {
        thread.start();
    }

    public void join() throws InterruptedException {
        thread.join();
    }

    public boolean isAlive() {
        return thread.isAlive();
    }

    public void interrupt() {
        thread.interrupt();
    }

    public boolean isInterrupted() {
        return thread.isInterrupted();
    }

    public void setPriority(int priority) {
        thread.setPriority(priority);
    }

    public int getPriority() {
        return thread.getPriority();
    }

    public String getName() {
        return thread.getName();
    }

    public void setName(String name) {
        thread.setName(name);
    }

    public Thread getThread() {
        return thread;
    }

    public Runnable getTask() {
        return task;
    }

    public boolean isStarted() {
        return started.get();
    }

    public boolean isFinished() {
        return finished.get();
    }

    public String getId() {
        return id;
    }

    public void setOnFinished(Runnable callback) {
        this.onFinishedCallback = callback;
    }

    @Override
    public String toString() {
        return "ThreadTask{" +
                "id='" + id + '\'' +
                ", name='" + getName() + '\'' +
                ", priority=" + getPriority() +
                ", isAlive=" + isAlive() +
                ", isInterrupted=" + isInterrupted() +
                ", isStarted=" + isStarted() +
                ", isFinished=" + isFinished() +
                '}';
    }
}
