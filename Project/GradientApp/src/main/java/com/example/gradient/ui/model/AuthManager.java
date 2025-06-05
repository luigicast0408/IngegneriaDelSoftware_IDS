package com.example.gradient.ui.model;

import com.example.gradient.database.User;
import com.example.gradient.database.UserDao;
import com.example.gradient.database.UserRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Objects;

public class AuthManager {

    private static AuthManager instance;
    private final UserDao userDao;
    private User currentUser;

    private AuthManager() {
        this.userDao = new UserRepository();
    }

    public static synchronized AuthManager getInstance() {
        if (instance == null) {
            instance = new AuthManager();
        }
        return instance;
    }

    /**
     * Login method to authenticate a user.
     *
     * @param username
     * @param password
     * @return \u00a0true if login is successful, false otherwise.
     */
    public boolean login(String username, String password) {
        User user = userDao.getUserByUsername(username);
        if (user != null && verifyPassword(password, user.getPassword())) {
            currentUser = user;
            return true;
        }
        return false;
    }

    /**
     * Register method to create a new user.
     * @return \u00a0true if registration is successful, false if the username is already in use.
     */
    public boolean register(String name, String surname, String email, String username, String password, String role) {
        if (userDao.getUserByUsername(username) != null) {
            return false;
        }

        User newUser = new User();
        setUserParameters(newUser, name, surname, email, username, password, role);
        userDao.createUser(newUser);
        return true;
    }

    /**
     * Update method to modify user details.
     */
    private void setUserParameters(User user, String name, String surname, String email, String username, String password, String role) {
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(hashPassword(password));
        user.setRole("ADMIN".equalsIgnoreCase(role) ? 1 : 0); // 1 = ADMIN, 0 = USER
    }


    public void logout() {
        currentUser = null;
    }
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    public String getCurrentUsername() {
        return currentUser != null ? currentUser.getUsername() : null;
    }
    public User getCurrentUser() {
        return currentUser;
    }

    public String getCurrentRole() {
        if (currentUser == null) return null;
        return currentUser.getRole() == 1 ? "ADMIN" : "USER";
    }

    public boolean isAdmin() {
        return currentUser != null && currentUser.getRole() == 1;
    }

    public boolean isUser() {
        return currentUser != null && currentUser.getRole() == 0;
    }

    /**
     * Password hashing and verification methods.
     * These methods use SHA-256 for hashing passwords.
     */
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 non disponibile", e);
        }
    }

    private boolean verifyPassword(String plainPassword, String hashedPassword) {
        return Objects.equals(hashPassword(plainPassword), hashedPassword);
    }
}
