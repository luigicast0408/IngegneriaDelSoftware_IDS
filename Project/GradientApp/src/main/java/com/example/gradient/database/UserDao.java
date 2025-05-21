package com.example.gradient.database;

import java.util.ArrayList;

public interface UserDao {
    void createUser(User user);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    User getUserById(int id);
    void updateUser(User user);
    void deleteUser(int id);
    User getUserByUsernameAndPassword(String username, String password);
    ArrayList<User> getAllUsers();
}