package com.example.gradient.ui.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainLayout extends BorderPane {
    private final Stage stage;
    private final LoadImageView loadImageView;
    private final LoginView loginView;
    private final RegisterView registerView;
    private final AdminDashboardView adminPanelView;

    private final Button loadBtn = new Button("Process Image");
    private final Button loginBtn = new Button("Login");
    private final Button registerBtn = new Button("Register");
    private final Button adminBtn = new Button("Admin Panel");
    private final Button logoutBtn = new Button("Logout");
    private final Button exitBtn = new Button("Exit");

    public MainLayout(Stage stage) {
        this.stage = stage;

        this.loadImageView = new LoadImageView();
        this.loginView = new LoginView();
        this.registerView = new RegisterView();
        this.adminPanelView = new AdminDashboardView();

        ToolBar toolBar = createToolBar();
        setTop(toolBar);
        setCenter(loadImageView.getRoot());

        configureButtonActions();
    }

    /*
     * This method is the entry point of the application.
     */
    public static void startApp(Stage stage) {
        MainLayout mainLayout = new MainLayout(stage);
        Scene scene = new Scene(mainLayout, 820, 720);
        stage.setTitle("Image Gradient Application");
        stage.setScene(scene);
        stage.show();
    }

    private ToolBar createToolBar() {
        ToolBar toolBar = new ToolBar();
        toolBar.getItems().addAll(loadBtn, loginBtn, registerBtn, adminBtn, logoutBtn, exitBtn);
        return toolBar;
    }

    /*
    This method sets on action events for the buttons in the toolbar.
    */
    private void configureButtonActions() {
        loadBtn.setOnAction(e -> setCenter(loadImageView.getRoot()));
        loginBtn.setOnAction(e -> setCenter(loginView.getRoot()));
        registerBtn.setOnAction(e -> setCenter(registerView.getRoot()));
        adminBtn.setOnAction(e -> setCenter(adminPanelView.getRoot()));

        logoutBtn.setOnAction(e -> {
            setCenter(loginView.getRoot());
            loginView.clearFields();
        });

        exitBtn.setOnAction(e -> stage.close());
    }
}
