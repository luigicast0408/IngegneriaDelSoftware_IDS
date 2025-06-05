package com.example.gradient.observer.logic;

import com.example.gradient.database.ImageEntity;
import com.example.gradient.database.User;

import java.util.List;

public class ObserverManager {
    public static final Subject<List<User>> USERS_SUBJECT = new Subject<>();
    public static final Subject<List<ImageEntity>> IMAGES_SUBJECT = new Subject<>();
}

