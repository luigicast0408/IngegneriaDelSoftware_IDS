package com.example.gradient.core;

import com.example.gradient.database.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserSessionTest {

    private User testUser;

    @BeforeEach
    void setup() {
        testUser = new User(1, "Mario", "Rossi", "mario@mail.com", "mario", "password", 0);
        UserSession.clear();
    }

    @Test
    void testStoreAndGetCurrentUser() {
        UserSession.setCurrentUser(testUser);
        User current = UserSession.getCurrentUser();

        assertNotNull(current, "Expected non-null user after setting");
        assertEquals("mario", current.getUsername());
        assertEquals("Mario", current.getName());
    }

    @Test
    void testClearSession() {
        UserSession.setCurrentUser(testUser);
        UserSession.clear();
        assertNull(UserSession.getCurrentUser(), "User should be null after clean");
    }

    @Test
    void testSimulatedIsLoggedIn() {
        assertNull(UserSession.getCurrentUser(), "Initially should be not logged in");

        UserSession.setCurrentUser(testUser);
        assertNotNull(UserSession.getCurrentUser(), "User should be logged in");

        UserSession.clear();
        assertNull(UserSession.getCurrentUser(), "User should be logged out");
    }
}
