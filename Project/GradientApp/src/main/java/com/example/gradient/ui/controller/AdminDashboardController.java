package com.example.gradient.ui.controller;

import com.example.gradient.database.*;
import com.example.gradient.ui.view.AdminDashboardView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class AdminDashboardController {
    private final AdminDashboardView view;
    private final ObservableList<ImageEntity> imageList;
    private final ObservableList<User> usersList;

    public AdminDashboardController(AdminDashboardView view) {
        this.view = view;
        this.imageList = FXCollections.observableArrayList();
        this.usersList = FXCollections.observableArrayList();
        initialize();
    }

    public void initialize() {
        bindUsersToView();
        bindImagesToView();
        loadUsersFromDB();
        loadImagesFromDB();
        setupAddImageAction();
        setupChooseImageAction();
        setupSaveProcessedImageAction();
    }

    private void loadUsersFromDB() {
        UserDao userDAO = new UserRepository();
        List<User> allUsers = userDAO.getAllUsers();
        usersList.setAll(allUsers);
    }

    private void loadImagesFromDB() {
        ImageDAO imageDAO = new ImageRepository();
        List<ImageEntity> allImages = imageDAO.getAllImages();
        imageList.setAll(allImages);
    }

    private void setupAddImageAction() {
        view.getAddToDbButton().setOnAction(e -> {
            String name = view.getNameField().getText();
            String path = view.getPathField().getText();
            String description = view.getDescriptionField().getText();

            if (name.isEmpty() || path.isEmpty()) {
                System.out.println("Name and Path are required");
                return;
            }

            ImageEntity newImage = new ImageEntity();
            newImage.setName(name);
            newImage.setPath(path);
            newImage.setDescription(description);
            imageList.add(newImage);

            view.getNameField().clear();
            view.getPathField().clear();
            view.getDescriptionField().clear();
        });
    }

    private void bindUsersToView() {
        view.getUserTable().setItems(usersList);

        if (view.getUserTable().getColumns().isEmpty()) {
            view.getUserTable().getColumns().clear();

            javafx.scene.control.TableColumn<User, String> usernameCol = new javafx.scene.control.TableColumn<>("Username");
            usernameCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("username"));

            javafx.scene.control.TableColumn<User, String> firstnameCol = new javafx.scene.control.TableColumn<>("First Name");
            firstnameCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("firstName"));

            javafx.scene.control.TableColumn<User, String> lastnameCol = new javafx.scene.control.TableColumn<>("Last Name");
            lastnameCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("lastName"));

            javafx.scene.control.TableColumn<User, String> emailCol = new javafx.scene.control.TableColumn<>("Email");
            emailCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("email"));

            view.getUserTable().getColumns().addAll(usernameCol, firstnameCol, lastnameCol, emailCol);
        }
    }

    private void bindImagesToView() {
        view.getImageTable().setItems(imageList);
    }

    private void setupChooseImageAction() {
        view.getRoot().lookupAll(".button").forEach(node -> {
            if (node instanceof javafx.scene.control.Button btn && btn.getText().equals("Choose Image")) {
                btn.setOnAction(e -> {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Select Image");
                    File selectedFile = fileChooser.showOpenDialog(view.getRoot().getScene().getWindow());
                    if (selectedFile != null) {
                        view.getNameField().setText(selectedFile.getName());
                        view.getPathField().setText(selectedFile.getAbsolutePath());
                    }
                });
            }
        });
    }

    private void setupSaveProcessedImageAction() {
        view.getSaveImageButton().setOnAction(e -> {
            System.out.println("Save Processed Image clicked");
            //TODO implement this method
        });
    }
}
