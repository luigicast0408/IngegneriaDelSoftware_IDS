package com.example.gradient.observer;

import com.example.gradient.database.User;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.util.List;

public class UserListObserver implements Observer<List<User>> {
    private final ObservableList<User> userList;

    public UserListObserver(ObservableList<User> userList) {
        this.userList = userList;
    }

    @Override
    public void update(List<User> data) {
        Platform.runLater(() -> {
            userList.setAll(data);
        });
    }
}


