package com.example.gradient.observer;

import com.example.gradient.database.ImageEntity;
import com.example.gradient.database.User;

import java.util.List;

public final class ObserverManager {
    public static final Subject<List<User>> USERS_SUBJECT   = new Subject<>();
    public static final Subject<List<ImageEntity>> IMAGES_SUBJECT = new Subject<>();
    public static final Subject<Double> PROGRESS_SUBJECT    = new Subject<>();

    private ObserverManager() {}

    public static void publishProgress(double value) {
        PROGRESS_SUBJECT.notifyObservers(value);
    }
}
