package com.example.gradient.ui.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SaveImageView {
    private final VBox root;
    private final ImageView originalImageView = new ImageView();
    private final ImageView processedImageView = new ImageView();
    private final Button saveImageButton = new Button("Save Processed Image");

    public SaveImageView() {
        originalImageView.setFitWidth(300);
        originalImageView.setFitHeight(300);
        originalImageView.setPreserveRatio(true);

        processedImageView.setFitWidth(300);
        processedImageView.setFitHeight(300);
        processedImageView.setPreserveRatio(true);

        HBox imagesBox = new HBox(30, originalImageView, processedImageView);
        imagesBox.setAlignment(Pos.CENTER);

        root = new VBox(15, imagesBox, saveImageButton);
        root.setAlignment(Pos.CENTER);
    }

    public VBox getRoot() { return root; }
    public ImageView getOriginalImageView() { return originalImageView; }
    public ImageView getProcessedImageView() { return processedImageView; }
    public Button getSaveImageButton() { return saveImageButton; }
}
