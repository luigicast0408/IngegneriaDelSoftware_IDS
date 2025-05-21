package com.example.gradient.ui.view;

import com.example.gradient.ui.controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class LoginView {
    private final VBox root;
    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Button loginButton = new Button("Login");
    private final Button registerButton = new Button("Register");
    private final Label errorLabel = new Label();

    public LoginView() {
        root = createRootLayout();
        GridPane gridPane = buildGridLayout();
        addComponentsToGrid(gridPane);
        root.getChildren().add(gridPane);
        new LoginController(this);
    }

    /**
     * Creates the root layout for the login view.
     *
     * @return VBox representing the root layout.
     */
    private VBox createRootLayout() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setPrefWidth(400);
        layout.setPrefHeight(300);
        return layout;
    }

    /**
     * Builds the grid layout for the login view.
     *
     * @return GridPane representing the grid layout.
     */
    private GridPane buildGridLayout() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }

    /**
     * Adds components to the grid layout.
     *
     * @param gridPane The grid pane to which components are added.
     */
    private void addComponentsToGrid(GridPane gridPane) {
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        usernameField.setPrefWidth(200);
        passwordField.setPrefWidth(200);

        loginButton.setPrefWidth(100);
        registerButton.setPrefWidth(100);
        loginButton.setDefaultButton(true);
        registerButton.setCancelButton(true);

        configureErrorLabel();

        gridPane.add(new Label("Username:"), 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 0, 2);
        gridPane.add(registerButton, 1, 2);
        gridPane.add(errorLabel, 0, 3, 2, 1);
    }

    private void configureErrorLabel() {
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setVisible(false);
        errorLabel.setWrapText(true);
        errorLabel.setMaxWidth(200);
        errorLabel.setAlignment(Pos.CENTER);
    }

    public VBox getRoot() {
        return root;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }


    public void setErrorLabel(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    public void clearErrorLabel() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }

    public void clearFields() {
        usernameField.clear();
        passwordField.clear();
        clearErrorLabel();
    }
}