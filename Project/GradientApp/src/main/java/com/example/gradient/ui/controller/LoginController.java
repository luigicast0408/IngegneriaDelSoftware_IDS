package com.example.gradient.ui.controller;

import com.example.gradient.core.UserSession;
import com.example.gradient.database.User;
import com.example.gradient.ui.model.AuthManager;
import com.example.gradient.ui.view.LoginView;
import com.sun.scenario.effect.impl.prism.PrRenderInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private final LoginView view;
    private  AuthManager authManager;

    public LoginController(LoginView view) {
        this.view = view;
        initialize();
    }

    private void initialize(){
        view.getLoginButton().setOnAction(e -> onLoginButtonClicked());
    }



    private void onLoginButtonClicked() {
        String username = view.getUsernameField().getText();
        String password = view.getPasswordField().getText();

        if (username.isBlank() || password.isBlank()) {
            showAlert("Error", "Insert username and password");
            return;
        }

        if (authManager.login(username, password)) {
            User user = authManager.getCurrentUser();
            UserSession.setCurrentUser(user);
            showAlert("Success", "Login success: " + user.getUsername());
        } else {
            showAlert("Error", "Invalid username or password");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
