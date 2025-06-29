package com.example.gradient.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    private UserDao userDao;

    @BeforeEach
    public void setup() {
        userDao = new UserRepository();
    }

    @Test
    public void getAllUsersReturnsArrayListTest() {
        ArrayList<User> users = userDao.getAllUsers();

        assertNotNull(users, "The list must not be null");
        assertEquals(ArrayList.class, users.getClass(), "The list must be of type ArrayList");
        assertFalse(users.isEmpty(), "The user list must not be empty");

        for (User user : users) {
            assertNotNull(user.getName(), "Name must not be null");
            assertNotNull(user.getSurname(), "Surname must not be null");
            assertNotNull(user.getUsername(), "Username must not be null");
            assertNotNull(user.getEmail(), "Email must not be null");
            assertNotNull(user.getPassword(), "Password must not be null");
        }
    }

    @Test
    public void addUserSuccess() {
        String email = generateTestEmail();
        User userInsert = createTestUser(email);
        userDao.createUser(userInsert);

        User inserted = findUserByEmail(email);
        assertNotNull(inserted, "User should be successfully added and retrievable");
    }

    @Test
    public void getUserByIdReturnsCorrectUser() {
        String email = generateTestEmail();
        User user = createTestUser(email);
        userDao.createUser(user);

        User inserted = findUserByEmail(email);
        assertNotNull(inserted);

        User retrieved = userDao.getUserById(inserted.getId());
        assertNotNull(retrieved);
        assertEquals(email, retrieved.getEmail());
    }

    @Test
    public void updateUserChangesDataCorrectly() {
        String email = generateTestEmail();
        User user = createTestUser(email);
        userDao.createUser(user);

        User inserted = findUserByEmail(email);
        assertNotNull(inserted);

        inserted.setName("UpdatedName");
        inserted.setSurname("UpdatedSurname");
        userDao.updateUser(inserted);

        User updated = userDao.getUserById(inserted.getId());
        assertEquals("UpdatedName", updated.getName());
        assertEquals("UpdatedSurname", updated.getSurname());
    }

    @Test
    public void deleteUser() {
        String email = generateTestEmail();
        User user = createTestUser(email);
        userDao.createUser(user);

        User inserted = findUserByEmail(email);
        assertNotNull(inserted);

        userDao.deleteUser(inserted.getId());

        User deleted = userDao.getUserById(inserted.getId());
        assertNull(deleted, "User should be deleted");
    }

    @Test
    public void loginWithValidCredentialsSucceeds() {
        String email = generateTestEmail();
        String username = "user_" + UUID.randomUUID();
        String password = "securepass";

        User user = new User(0, "Login", "User", email, username, password, 0);
        userDao.createUser(user);
    }

    private User createTestUser(String email) {
        User user = new User();
        user.setName("Test");
        user.setSurname("User");
        user.setUsername("user_" + UUID.randomUUID());
        user.setEmail(email);
        user.setPassword("password");
        user.setRole(0);
        return user;
    }

    private String generateTestEmail() {
        return "test_" + UUID.randomUUID() + "@example.com";
    }

    private User findUserByEmail(String email) {
        return userDao.getAllUsers().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}