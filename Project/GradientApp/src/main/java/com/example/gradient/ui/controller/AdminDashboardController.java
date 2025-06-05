package com.example.gradient.ui.controller;

import com.example.gradient.core.UserSession;
import com.example.gradient.database.*;
import com.example.gradient.observer.logic.ImageListObserver;
import com.example.gradient.observer.logic.ObserverManager;
import com.example.gradient.observer.logic.Subject;
import com.example.gradient.observer.logic.UserListObserver;
import com.example.gradient.ui.view.AdminDashboardView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class AdminDashboardController {
    private final AdminDashboardView view;
    private final ObservableList<ImageEntity> imageList;
    private final ObservableList<User> usersList;
    private final Subject<List<User>> usersSubject = new Subject<>();

    public AdminDashboardController(AdminDashboardView view) {
        this.view = view;
        this.imageList = FXCollections.observableArrayList();
        this.usersList = FXCollections.observableArrayList();

        ObserverManager.USERS_SUBJECT.addObserver(new UserListObserver(usersList));
        ObserverManager.IMAGES_SUBJECT.addObserver(new ImageListObserver(imageList));
        initialize();
    }

    public void initialize() {
        bindUsersToView();
        bindImagesToView();
        loadUsersFromDB();
        loadImagesFromDB();
        setupAddImageAction();
        setupChooseImageAction();
    }

    /**
     * Executes a query to the database to retrieve all users and updates the {@code usersList}.
     * <p>
     * Uses the Observer design pattern to update the list when a user is registered.
     * This operation is performed in a separate thread using a {@code Task}
     * to prevent blocking the JavaFX Application Thread and keep the GUI responsive.
     * </p>
     */

    private void loadUsersFromDB() {
        Task<List<User>> task = new Task<>() {
            @Override
            protected List<User> call() {
                UserDao userDAO = new UserRepository();
                return userDAO.getAllUsers();
            }

            @Override
            protected void succeeded() {
                ObserverManager.USERS_SUBJECT.notifyObservers(getValue());
            }

            @Override
            protected void failed() {
                System.err.println("Error in users load: " + getException());
            }
        };

        new Thread(task).start();
    }

    private void loadImagesFromDB() {
        Task<List<ImageEntity>> task = new Task<>() {

            @Override
            protected List<ImageEntity> call() throws Exception {
                ImageDAO imageDAO = new ImageRepository();
                return imageDAO.getAllImages();
            }

            protected void succeeded() {
                ObserverManager.IMAGES_SUBJECT.notifyObservers(getValue());
            }

            @Override
            protected void failed() {
                System.err.println("Error in images load: " + getException());
            }
        };
        new Thread(task).start();
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
                    File selectedFile = chooseImageFile();
                    if (selectedFile != null) {
                        try {
                            File copiedFile = copyImageToLocalFolder(selectedFile);
                            handleSelectedImage(copiedFile);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private File chooseImageFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        return fileChooser.showOpenDialog(view.getRoot().getScene().getWindow());
    }

    private File copyImageToLocalFolder(File selectedFile) throws Exception {
        File imagesDir = new File(System.getProperty("user.dir"), "images");
        if (!imagesDir.exists()) {
            boolean created = imagesDir.mkdirs();
        }

        File destFile = new File(imagesDir, selectedFile.getName());
        java.nio.file.Files.copy(selectedFile.toPath(), destFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        return destFile;
    }

    private void handleSelectedImage(File copiedFile) {
        String fileName = copiedFile.getName();
        String relativePath = "images/" + fileName;

        view.getNameField().setText(fileName);
        view.getPathField().setText(relativePath);

        ImageEntity image = new ImageEntity();
        image.setName(fileName);
        image.setPath(relativePath);
        image.setDescription(view.getDescriptionField().getText());
        image.setId_user(UserSession.getCurrentUser().getId());

        ImageDAO imageDAO = new ImageRepository();
        imageDAO.insertImage(image);
        imageList.add(image);
    }
}