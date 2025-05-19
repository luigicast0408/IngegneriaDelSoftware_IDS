package com.example.gradient.ui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class RegisterView {
    private final VBox root;
    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final PasswordField confirmPasswordField = new PasswordField();
    private final Button registerButton = new Button("Register");
    private final Label messageLabel = new Label();

    public RegisterView() {
        root = createRootLayout();
        GridPane form = createForm();
        root.getChildren().add(form);
    }

    private VBox createRootLayout() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setPrefWidth(400);
        layout.setPrefHeight(300);
        return layout;
    }
    /**
     * Creates the form layout for the registration view.
     *
     * @return GridPane representing the form layout.
     */

    private GridPane createForm() {
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        confirmPasswordField.setPromptText("Confirm Password");
        registerButton.setPrefWidth(120);
        configureMessageLabel();

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(new Label("Confirm Password:"), 0, 2);
        grid.add(confirmPasswordField, 1, 2);
        grid.add(registerButton, 1, 3);
        grid.add(messageLabel, 0, 4, 2, 1);

        return grid;
    }

    private void configureMessageLabel() {
        messageLabel.setStyle("-fx-text-fill: red;");
        messageLabel.setVisible(false);
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(300);
        messageLabel.setAlignment(Pos.CENTER);
    }

    public VBox getRoot() { return root; }
    public TextField getUsernameField() { return usernameField; }
    public PasswordField getPasswordField() { return passwordField; }
    public PasswordField getConfirmPasswordField() { return confirmPasswordField; }
    public Button getRegisterButton() { return registerButton; }


    public void setMessage(String text, String color) {
        messageLabel.setText(text);
        messageLabel.setStyle("-fx-text-fill: " + color + ";");
        messageLabel.setVisible(true);
    }

    public void clearFields() {
        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        messageLabel.setVisible(false);
    }
}