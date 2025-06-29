package com.example.gradient.ui.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthManagerTest {

    private AuthManager authManager;

    @BeforeEach
    void setUp() {
        authManager = new AuthManager();
    }

    @Test
    void successfulLogin() {
        authManager.register("Mario", "Rossi", "mario@mail.com", "mario", "pass", 0);
        assertTrue(authManager.login("mario", "pass"));
        assertEquals("mario", authManager.getCurrentUsername());
    }

    @Test
    void failedLoginWrongPassword() {
        authManager.register("Luigi", "Bianchi", "luigi@gmail.com", "luigi", "pass", 0);
        assertFalse(authManager.login("luigi", "wrongpass"));
    }

    @Test
    void registrationFailsOnDuplicateUsername() {
        authManager.register("Anna", "Verdi", "anna@mail.com", "anna", "123", 0);
        boolean result = authManager.register("Anna", "Verdi", "anna2@mail.com", "anna", "123", 0);
        assertFalse(result);
    }

    @Test
    void logoutClearsCurrentUser() {
        authManager.register("User", "Test", "u@test.com", "usertest", "1234", 0);
        authManager.login("user_test", "1234");
        authManager.logout();
        assertFalse(authManager.isLoggedIn());
        assertNull(authManager.getCurrentUser());
    }

    @Test
    void successfulLoginAdmin(){
        authManager.logout();
    }
}
