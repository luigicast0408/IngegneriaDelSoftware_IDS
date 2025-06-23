package com.example.gradient.ui.controller;

import com.example.gradient.core.ImageProcessor;
import com.example.gradient.core.ThreadTask;
import com.example.gradient.database.*;
import com.example.gradient.ui.view.LoadImageView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LoadImagesController {
    private final LoadImageView view;
    private final ObservableList<ImageEntity> imageList = FXCollections.observableArrayList();
    private ImageProcessor imageProcessor;
    private Image originalImage;

    public LoadImagesController(LoadImageView view) {
        this.view = view;
        this.view.getImageTableView().setItems(imageList);
        setupListeners();
        loadImages();
    }

    private void loadImages() {
        ImageDAO dao = new ImageRepository();
        List<ImageEntity> all = dao.getAllImages();
        imageList.setAll(all);
    }

    private void setupListeners() {
        view.getImageTableView().getSelectionModel()
                .selectedItemProperty()
                .addListener((o,oldVal,newVal) -> {
                    if (newVal != null) handleImageSelection(newVal);
                });

        view.getAlgorithmComboBox().getSelectionModel()
                .selectedItemProperty()
                .addListener((o,oldVal,newVal) -> {
                    if (newVal != null) handleAlgorithmSelection(newVal);
                });
    }

    private void handleImageSelection(ImageEntity ent) {
        loadOriginalImage(ent);
        initializeProcessorIfReady();
    }

    private void handleAlgorithmSelection(String alg) {
        if (imageProcessor == null || originalImage == null) return;
        processImageAsync(alg);
    }

    private void initializeProcessorIfReady() {
        if (originalImage != null) {
            imageProcessor = new ImageProcessor("sobel", originalImage);
            processImageAsync("sobel");
        }
    }

    private void processImageAsync(String alg) {
        ProgressBar bar = view.getProgressBar();
        Platform.runLater(() -> {
            bar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            bar.setVisible(true);
        });

        Runnable taskRun = () -> {
            try {
                Thread.sleep(4000);
            }
            catch (InterruptedException ignored) {

            }
            imageProcessor.setAlgorithm(alg);
            imageProcessor.process();
        };

        ThreadTask t = new ThreadTask(taskRun);
        t.setOnFinished(() -> Platform.runLater(() -> {
            view.getProcessedImageView().setImage(imageProcessor.getResult());
            bar.setVisible(false); bar.setProgress(0);
        }));
        t.start();
    }

    private void loadOriginalImage(ImageEntity image) {
        Path p = Paths.get(image.getPath());
        if (!p.isAbsolute()) p = Paths.get(System.getProperty("user.dir")).resolve(p);

        if (Files.exists(p)) {
            Image img = new Image(p.toUri().toString());
            originalImage = img;
            Platform.runLater(() -> view.getOriginalImageView().setImage(img));
        } else {
            System.err.println("File not found: " + p);
            originalImage = null;
            Platform.runLater(() -> view.getOriginalImageView().setImage(null));
        }
    }
}
