package com.example.gradient.ui.controller;

import com.example.gradient.core.SceneManager;
import com.example.gradient.ui.model.AuthManager;
import com.example.gradient.ui.view.LoginView;
import com.example.gradient.ui.view.RegisterView;
import javafx.scene.control.Alert;

public class RegistrationController {

    private final RegisterView view;
    private final AuthManager authManager = AuthManager.getInstance();

    public RegistrationController(RegisterView view){
        this.view = view;
        initialize();
    }

    private void initialize() {
        view.getRegisterButton().setOnAction(e -> onRegisterClicked());
    }


    private void onRegisterClicked() {
        String name = view.getNameField().getText();
        String surname = view.getSurnameField().getText();
        String email = view.getEmailField().getText();
        String username = view.getUsernameField().getText();
        String password = view.getPasswordField().getText();
        String confirmPassword =  view.getConfirmPasswordField().getText();

        if (name.isBlank() || surname.isBlank() || email.isBlank()
                || username.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            showAlert("Validation Error", "Please fill in all fields.");
            return;
        }

        if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showAlert("Validation Error", "Invalid email format.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Validation Error", "Passwords do not match.");
            return;
        }

        if (password.length() < 8 || !password.matches(".*\\d.*") || !password.matches(".*[A-Z].*")) {
            showAlert("Validation Error", "Password must be at least 8 characters, contain a number and an uppercase letter.");
            return;
        }

        if (authManager.register(name, surname, email, username, password, "USER")) {
            showAlert("Success", "The user is registered successfully.");
            SceneManager.switchTo(new LoginView().getRoot());
        } else {
            showAlert("Registration Failed", "Username is already in use.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
