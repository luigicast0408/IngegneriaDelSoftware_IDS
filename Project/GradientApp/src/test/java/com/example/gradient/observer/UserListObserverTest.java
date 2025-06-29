package com.example.gradient.observer;

import com.example.gradient.database.UserDao;
import com.example.gradient.database.User;
import com.example.gradient.database.UserRepository;
import com.example.gradient.util.JavaFXInitializer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class UserListObserverTest {

    @BeforeAll
    static void setupJavaFX(){
        JavaFXInitializer.init();
    }

    @Test
    void shouldUpdateUserListFromDatabase() throws Exception {
        List<User> usersFromDb = fetchFomDB();
        assertNotNull(usersFromDb);
        assertFalse(usersFromDb.isEmpty(), "Users table must contain at least one user");

        ObservableList<User> observableList = FXCollections.observableArrayList();
        UserListObserver observer = new UserListObserver(observableList);

        runOnFxThreadAndWait(() -> observer.update(usersFromDb));

        assertEquals(usersFromDb.size(), observableList.size(), "Mismatch in user list size");
        for (int i = 0; i < usersFromDb.size(); i++) {
            assertEquals(usersFromDb.get(i).getUsername(), observableList.get(i).getUsername(), "Username mismatch");
            assertEquals(usersFromDb.get(i).getEmail(), observableList.get(i).getEmail(), "Email mismatch");
        }
    }


    private void runOnFxThreadAndWait(Runnable action) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            action.run();
            Platform.runLater(latch::countDown);
        });
        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Timeout during JavaFX thread execution");
        }
    }

    private List<User> fetchFomDB(){
        UserDao userDao = new UserRepository();
        return userDao.getAllUsers();
    }
}


