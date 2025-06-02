package com.example.gradient.ui.controller;

import com.example.gradient.core.ImageProcessor;
import com.example.gradient.core.ThreadTask;
import com.example.gradient.database.ImageDAO;
import com.example.gradient.database.ImageEntity;
import com.example.gradient.database.ImageRepository;
import com.example.gradient.ui.view.LoadImageView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.ArrayList;

public class LoadImagesController {
    private final LoadImageView view;
    private final ObservableList<ImageEntity> imageList;
    private ImageProcessor imageProcessor;
    private Image originalImage;

    public LoadImagesController(LoadImageView view) {
        this.view = view;
        this.imageList = FXCollections.observableArrayList();
        this.view.getImageTableView().setItems(this.imageList);
        setupListeners();
        loadImages();
    }

    private void loadImages() {
        ArrayList<ImageEntity> imageDB = fetchImagesFromDatabase();
        imageList.setAll(imageDB);
    }

    private void setupListeners() {
        setupSelectionListener();
        setupAlgorithmSelectionListener();
    }

    private void setupSelectionListener() {
        view.getImageTableView()
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        handleImageSelection(newSel);
                    }
                });
    }

    private void setupAlgorithmSelectionListener() {
        view.getAlgorithmComboBox()
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldAlg, newAlg) -> {
                    if (newAlg != null) {
                        handleAlgorithmSelection(newAlg);
                    }
                });
    }

    private void handleImageSelection(ImageEntity selectedImage) {
        loadOriginalImage(selectedImage);
        originalImage = view.getOriginalImageView().getImage();
        initializeProcessorIfReady();
    }

    private void handleAlgorithmSelection(String algorithm) {
        if (imageProcessor == null || originalImage == null) {
            System.err.println("ImageProcessor or original image not initialized.");
            return;
        }
        processImageAsync(algorithm);
    }

    private void initializeProcessorIfReady() {
        if (originalImage != null) {
            imageProcessor = new ImageProcessor("sobel", originalImage);
            processImageAsync("sobel");
        }
    }

    private void processImageAsync(String algorithm) {
        showProgressBar();

        Runnable taskRunnable = createImageProcessingTask(algorithm);
        ThreadTask threadTask = new ThreadTask(taskRunnable);

        setupTaskHandler(threadTask);
        threadTask.start();
    }

    private void showProgressBar() {
        Platform.runLater(() -> {
            ProgressBar progressBar = view.getProgressBar();
            progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            progressBar.setVisible(true);
        });
    }

    private Runnable createImageProcessingTask(String algorithm) {
        return () -> {
            try {
                imageProcessor.setAlgorithm(algorithm);
                Thread.sleep(3000);
                imageProcessor.process();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted during sleep");
                Thread.currentThread().interrupt();
            }
        };
    }

    private void setupTaskHandler(ThreadTask threadTask) {
        threadTask.setOnFinished(() -> Platform.runLater(() -> {
            view.getProcessedImageView().setImage(imageProcessor.getResult());
            ProgressBar progressBar = view.getProgressBar();
            progressBar.setVisible(false);
            progressBar.setProgress(0);
        }));
    }

    private void loadOriginalImage(ImageEntity image) {
        String relativePath = image.getPath();
        try (InputStream is = getClass().getResourceAsStream("/images/lena.png")) {
            if (is == null) {
                System.err.println("Resource not found: " + relativePath);
                view.getOriginalImageView().setImage(null);
                return;
                //TODO why when i  put the relativaPath dont work?
            }
            Image img = new Image(is);
            view.getOriginalImageView().setImage(img);
            System.out.println("Image loaded successfully from: " + relativePath);
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            view.getOriginalImageView().setImage(null);
        }
    }

    private ArrayList<ImageEntity> fetchImagesFromDatabase() {
        ImageDAO imageDAO = new ImageRepository();
        return (ArrayList<ImageEntity>) imageDAO.getAllImages();
    }
}
