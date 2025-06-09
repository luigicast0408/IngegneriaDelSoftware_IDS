package com.example.gradient.observer;

import com.example.gradient.database.ImageEntity;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.util.List;

public class ImageListObserver implements Observer<List<ImageEntity>> {
    private final ObservableList<ImageEntity> observableImages;

    public ImageListObserver(ObservableList<ImageEntity> observableImages) {
        this.observableImages = observableImages;
    }

    @Override
    public void update(List<ImageEntity> images) {
        Platform.runLater(() -> {
            observableImages.setAll(images);
        });
    }
}
