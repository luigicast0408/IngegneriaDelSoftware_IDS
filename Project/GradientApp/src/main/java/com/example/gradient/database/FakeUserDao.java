package com.example.gradient.database;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class used to generate a fake user with random data,
 * primarily for authentication-related tests (AuthManager).
 */

public class FakeUserDao implements UserDao {

    private final Map<String, User> users = new HashMap<>();
    private int idCounter = 1;

    @Override
    public void createUser(User user) {
        user.setId(idCounter++);
        users.put(user.getUsername(), user);
    }

    @Override
    public User getUserByUsername(String username) {
        return users.get(username);
    }

    @Override
    public User getUserByEmail(String email) {
        return (User) users.values().stream()
                .filter(user -> user.getEmail().equals(email));
    }

    @Override
    public User getUserById(int id) {
        return users.values().stream()
                .filter(user -> user.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public void updateUser(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public void deleteUser(int id) {
        users.values().removeIf(user -> user.getId() == id);
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        return (User) users.values().stream()
                .filter(user -> user.getPassword().equals(password)
                        && user.getUsername().equals(username)
                );
    }

    @Override
    public java.util.ArrayList<User> getAllUsers() {
        return new java.util.ArrayList<>(users.values());
    }
}