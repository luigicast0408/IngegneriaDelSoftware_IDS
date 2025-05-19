package com.example.gradient.ui.view;

import com.example.gradient.database.ImageEntity;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class AdminDashboardView {
    private final VBox root;

    private final TextField nameField = new TextField();
    private final TextField pathField = new TextField();
    private final TextField descriptionField = new TextField();
    private final Button addToDbButton = new Button("Add to Database");

    private final TableView<ImageEntity> imageTable = new TableView<>();
    private final ObservableList<ImageEntity> imageList = FXCollections.observableArrayList();

    private final TableView<String> userTable = new TableView<>();
    private final ObservableList<String> userList = FXCollections.observableArrayList();

    private final ImageView originalImageView = new ImageView();
    private final ImageView processedImageView = new ImageView();
    private final Button saveImageButton = new Button("Save Processed Image");

    public AdminDashboardView() {
        root = new VBox(25);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        VBox formBox = createFormBox();
        VBox userBox = createUserTableBox();
        VBox imageBox = createImageTableBox();
        VBox saveImageBox = createImageDisplayBox();

        root.getChildren().addAll(formBox, userBox, imageBox, saveImageBox);
    }

    /**
     * Creates the form layout for saving images to the database.
     *
     * @return VBox representing the form layout.
     */
    private VBox createFormBox() {
        Label formLabel = new Label("Save Image to Database");

        nameField.setPromptText("Image Name");
        pathField.setPromptText("Image Path");
        descriptionField.setPromptText("Description");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setAlignment(Pos.CENTER);
        form.add(new Label("Name:"), 0, 0);
        form.add(nameField, 1, 0);
        form.add(new Label("Path:"), 0, 1);
        form.add(pathField, 1, 1);
        form.add(new Label("Description:"), 0, 2);
        form.add(descriptionField, 1, 2);
        form.add(addToDbButton, 1, 3);

        VBox formBox = new VBox(10, formLabel, form);
        formBox.setStyle("-fx-border-color: lightgray; -fx-padding: 10;");
        formBox.setAlignment(Pos.CENTER);
        return formBox;
    }

    private VBox createUserTableBox() {
        Label userLabel = new Label("User List");
        TableColumn<String, String> userCol = new TableColumn<>("Username");
        userCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue()));
        userCol.setPrefWidth(300);

        userTable.getColumns().add(userCol);
        userTable.setItems(userList);
        userTable.setPrefHeight(150);

        return new VBox(5, userLabel, userTable);
    }

    /**
     * Creates the image table layout for displaying images.
     *
     * @return VBox representing the image table layout.
     */
    private VBox createImageTableBox() {
        Label imageLabel = new Label("Image List");

        TableColumn<ImageEntity, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        nameCol.setPrefWidth(150);

        TableColumn<ImageEntity, String> pathCol = new TableColumn<>("Path");
        pathCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPath()));
        pathCol.setPrefWidth(300);

        TableColumn<ImageEntity, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getDescription()));
        descCol.setPrefWidth(250);

        imageTable.getColumns().addAll(nameCol, pathCol, descCol);
        imageTable.setItems(imageList);
        imageTable.setPrefHeight(200);

        return new VBox(5, imageLabel, imageTable);
    }

    private VBox createImageDisplayBox() {
        configureImageView(originalImageView);
        configureImageView(processedImageView);

        HBox imagesBox = new HBox(30, originalImageView, processedImageView);
        imagesBox.setAlignment(Pos.CENTER);

        VBox saveImageBox = new VBox(15, imagesBox, saveImageButton);
        saveImageBox.setAlignment(Pos.CENTER);
        saveImageBox.setStyle("-fx-border-color: lightgray; -fx-padding: 10;");
        return saveImageBox;
    }

    private void configureImageView(ImageView imageView) {
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);
        imageView.setPreserveRatio(true);
    }

    public VBox getRoot() { return root; }

    public TextField getNameField() { return nameField; }
    public TextField getPathField() { return pathField; }
    public TextField getDescriptionField() { return descriptionField; }
    public Button getAddToDbButton() { return addToDbButton; }

    public TableView<ImageEntity> getImageTable() { return imageTable; }
    public ObservableList<ImageEntity> getImageList() { return imageList; }

    public TableView<String> getUserTable() { return userTable; }
    public ObservableList<String> getUserList() { return userList; }

    public ImageView getOriginalImageView() { return originalImageView; }
    public ImageView getProcessedImageView() { return processedImageView; }
    public Button getSaveImageButton() { return saveImageButton; }
}