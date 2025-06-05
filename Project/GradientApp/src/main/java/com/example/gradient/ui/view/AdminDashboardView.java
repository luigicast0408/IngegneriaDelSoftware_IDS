package com.example.gradient.ui.view;

import com.example.gradient.database.ImageEntity;
import com.example.gradient.database.User;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private final TableView<User> userTable = new TableView<>();

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
        root.getChildren().addAll(formBox, userBox, imageBox);
    }

    private VBox createFormBox() {
        Label formLabel = new Label("Save Image to Database");
        Button chooseFileButton = new Button("Choose Image");
        descriptionField.setPromptText("Description");

        VBox formBox = new VBox(10,
                formLabel,
                chooseFileButton,
                new Label("Description:"),
                descriptionField,
                addToDbButton
        );
        formBox.setStyle("-fx-border-color: lightgray; -fx-padding: 10;");
        formBox.setAlignment(Pos.CENTER);
        return formBox;
    }


    private VBox createUserTableBox() {
        Label userLabel = new Label("Users List");
        setupUserTable();
        userTable.setPrefHeight(150);
        return new VBox(5, userLabel, userTable);
    }

    private void setupUserTable() {
        TableColumn<User, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, String> surnameCol = new TableColumn<>("Surname");
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        userTable.getColumns().setAll(nameCol, surnameCol, emailCol, roleCol);
    }


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
    public TableView<User> getUserTable() { return userTable; }
    public ImageView getOriginalImageView() { return originalImageView; }
    public ImageView getProcessedImageView() { return processedImageView; }
    public Button getSaveImageButton() { return saveImageButton; }
}