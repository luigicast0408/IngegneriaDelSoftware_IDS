package com.example.gradient.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class MainViewController {

    @FXML private Button loadImageButton;
    @FXML private TextField imagePathField;
    @FXML private ComboBox<String> algorithmComboBox;
    @FXML private Button calculateButton;
    @FXML private ProgressBar progressBar;
    @FXML private ImageView originalImageView;
    @FXML private ImageView processedImageView;
    @FXML private Button saveImageButton;

    public MainViewController() {
        // Costruttore vuoto richiesto da JavaFX
    }

    @FXML
    private void initialize() {
        if (algorithmComboBox != null) {
            algorithmComboBox.getItems().addAll("Sobel", "Roberts", "Prewitt");
            algorithmComboBox.getSelectionModel().selectFirst();
        }

        loadImageButton.setOnAction(event -> handleLoadImage());
        calculateButton.setOnAction(event -> handleCalculateGradient());
        saveImageButton.setOnAction(event -> handleSaveImage());
    }

    public void handleLoadImage() {
        System.out.println("Load image clicked");

    }

    public void handleCalculateGradient() {
        System.out.println("Calculate gradient clicked");

    }

    public void handleSaveImage() {
        System.out.println("Save image clicked");

    }
}
