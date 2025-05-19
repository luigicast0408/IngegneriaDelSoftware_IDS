package com.example.gradient.ui.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoadImageView {
    private final VBox root;
    private final Button loadImageButton = new Button("Load Image");
    private final TextField imagePathField = new TextField();
    private final ComboBox<String> algorithmComboBox = new ComboBox<>();
    private final Button calculateButton = new Button("Calculate Gradient");
    private final ProgressBar progressBar = new ProgressBar(0);
    private final ImageView originalImageView = new ImageView();
    private final ImageView processedImageView = new ImageView();

    public LoadImageView() {
        root = createRootLayout();
        root.getChildren().addAll(createImageLoaderBox(), createAlgorithmBox(), createProgressBar(), createImageDisplayBox());
    }

    /**
     * Creates the root layout for the LoadImageView.
     *
     * @return VBox representing the root layout.
     */


    private VBox createRootLayout() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPrefWidth(820);
        return layout;
    }

    /**
     * Creates the image loader box with a button and text field.
     *
     * @return HBox containing the image loader components.
     */
    private HBox createImageLoaderBox() {
        imagePathField.setPromptText("Image Path");
        imagePathField.setEditable(false);
        HBox.setHgrow(imagePathField, Priority.ALWAYS);

        HBox loaderBox = new HBox(10, loadImageButton, imagePathField);
        loaderBox.setAlignment(Pos.CENTER);
        loaderBox.setPrefWidth(780);
        return loaderBox;
    }

    /**
     * Creates the algorithm selection box with a combo box and calculate button.
     *
     * @return HBox containing the algorithm selection components.
     */
    private HBox createAlgorithmBox() {
        algorithmComboBox.setPromptText("Select Algorithm");
        algorithmComboBox.setItems(FXCollections.observableArrayList("Sobel", "Prewitt", "Roberts"));

        HBox algorithmBox = new HBox(10, algorithmComboBox, calculateButton);
        algorithmBox.setAlignment(Pos.CENTER);
        return algorithmBox;
    }

    private ProgressBar createProgressBar() {
        progressBar.setPrefWidth(780);
        return progressBar;
    }


    /**
     * Creates the image display box with original and processed images.
     *
     * @return HBox containing the original and processed image views.
     */

    private HBox createImageDisplayBox() {
        configureImageView(originalImageView);
        configureImageView(processedImageView);

        VBox originalBox = new VBox(5, new Text("Original Image"), originalImageView);
        originalBox.setAlignment(Pos.CENTER);

        VBox processedBox = new VBox(5, new Text("Processed Image"), processedImageView);
        processedBox.setAlignment(Pos.CENTER);

        HBox imageBox = new HBox(30, originalBox, processedBox);
        imageBox.setAlignment(Pos.CENTER);
        return imageBox;
    }

    /**
     * Configures the ImageView properties.
     *
     * @param imageView The ImageView to configure.
     */
    private void configureImageView(ImageView imageView) {
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);
        imageView.setPreserveRatio(true);
    }

    public VBox getRoot() {
        return root;
    }

    public Button getLoadImageButton() {
        return loadImageButton;
    }

    public TextField getImagePathField() {
        return imagePathField;
    }

    public ComboBox<String> getAlgorithmComboBox() {
        return algorithmComboBox;
    }

    public Button getCalculateButton() {
        return calculateButton;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public ImageView getOriginalImageView() {
        return originalImageView;
    }

    public ImageView getProcessedImageView() {
        return processedImageView;
    }
}
