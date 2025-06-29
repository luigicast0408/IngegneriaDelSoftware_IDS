package com.example.gradient.observer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubjectTest {

    private Subject<String> subject;
    private List<String> notifications;

    @BeforeEach
    void setUp() {
        subject = new Subject<>();
        notifications = new ArrayList<>();
    }

    @Test
    void testAddObserverAndNotify() {
        Observer<String> observer = notifications::add;
        subject.addObserver(observer);

        subject.notifyObservers("Hello");

        assertEquals(1, notifications.size());
        assertEquals("Hello", notifications.get(0));
    }

    @Test
    void testRemoveObserver() {
        Observer<String> observer = notifications::add;
        subject.addObserver(observer);
        subject.removeObserver(observer);

        subject.notifyObservers("Hello");

        assertTrue(notifications.isEmpty());
    }

    @Test
    void testMultipleObservers() {
        Observer<String> observer1 = msg -> notifications.add("1: " + msg);
        Observer<String> observer2 = msg -> notifications.add("2: " + msg);

        subject.addObserver(observer1);
        subject.addObserver(observer2);

        subject.notifyObservers("Update");

        assertEquals(2, notifications.size());
        assertTrue(notifications.contains("1: Update"));
        assertTrue(notifications.contains("2: Update"));
    }
}