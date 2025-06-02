package com.example.gradient.ui.view;

import com.example.gradient.database.ImageEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoadImageView {
    private final VBox root;
    private final TableView<ImageEntity> imageTableView = new TableView<>();
    private final Button loadImageButton = new Button("Load Image");
    private final ComboBox<String> algorithmComboBox = new ComboBox<>();
    private final ComboBox<String> actionComboBox = new ComboBox<>();
    private final Button calculateButton = new Button("Calculate Gradient");
    private final ProgressBar progressBar = new ProgressBar(0);
    private final ImageView originalImageView = new ImageView();
    private final ImageView processedImageView = new ImageView();
    private final Button playButton = new Button("Play");
    private final Button pauseButton = new Button("Pause");
    private final Button resumeButton = new Button("Resume");

    public LoadImageView() {
        root = createRootLayout();
        root.getChildren().addAll(
                createImageListSection(),
                createAlgorithmBox(),
                createProgressBar(),
                createImageDisplayBox()
        );
    }

    private VBox createRootLayout() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPrefWidth(820);
        return layout;
    }

    private VBox createImageListSection() {
        imageTableView.setPrefHeight(150);
        imageTableView.setPrefWidth(780);


        TableColumn<ImageEntity, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        nameCol.setPrefWidth(200);
        
        TableColumn<ImageEntity, String> pathCol = new TableColumn<>("Path");
        pathCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPath()));
        pathCol.setPrefWidth(300);

        TableColumn<ImageEntity, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        descCol.setPrefWidth(280);
        imageTableView.getColumns().addAll(nameCol, pathCol, descCol);

        VBox box = new VBox(5, new Label("Saved Images:"), imageTableView);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }


    private VBox createAlgorithmBox() {
        algorithmComboBox.setPromptText("Select Algorithm");
        algorithmComboBox.setItems(FXCollections.observableArrayList("Sobel", "Prewitt", "Roberts"));

        Button playButton = new Button("Play");
        Button resumeButton = new Button("Resume");


        HBox topRow = new HBox(10, algorithmComboBox, calculateButton);
        topRow.setAlignment(Pos.CENTER);

        HBox bottomRow = new HBox(10, playButton, resumeButton, pauseButton);
        bottomRow.setAlignment(Pos.CENTER);

        VBox algorithmBox = new VBox(10, topRow, bottomRow);
        algorithmBox.setAlignment(Pos.CENTER);
        return algorithmBox;
    }

    private ProgressBar createProgressBar() {
        progressBar.setPrefWidth(780);
        return progressBar;
    }

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

    private void configureImageView(ImageView imageView) {
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);
        imageView.setPreserveRatio(true);
    }

    public VBox getRoot() {
        return root;
    }

    public TableView<ImageEntity> getImageTableView(){return imageTableView;}
    public Button getLoadImageButton() {
        return loadImageButton;
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
    public  ComboBox<String> getActionComboBox(){return  actionComboBox;}
    public Button getPlayButton() {return playButton;}
    public  Button getResumeButton() {return  resumeButton;}
    public  Button getPauseButton() {return pauseButton;}

}
