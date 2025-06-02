package com.example.gradient.ui.view;

import com.example.gradient.core.SceneManager;
import com.example.gradient.ui.controller.AdminDashboardController;
import com.example.gradient.ui.controller.LoadImagesController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The MainLayout class defines the main user interface layout for the application.
 * It manages navigation between different views using a central dynamic container.
 */
public class MainLayout extends BorderPane {
    private final Stage stage;
    private final StackPane contentPane = new StackPane();

    private final LoadImageView loadImageView;
    private final LoadImagesController loadImagesController;

    private final AdminDashboardController adminDashboardController;

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
        this.loadImagesController = new LoadImagesController(loadImageView);
        this.adminDashboardController = new AdminDashboardController(adminPanelView);

        SceneManager.setMainContainer(contentPane);
        SceneManager.switchTo(loginView.getRoot());

        ToolBar toolBar = createToolBar();
        setTop(toolBar);
        setCenter(contentPane);

        configureButtonActions();
    }

    public static void startApp(Stage stage) {
        MainLayout mainLayout = new MainLayout(stage);
        Scene scene = new Scene(mainLayout, 820, 720);
        stage.setTitle("Image Gradient Application");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates and returns the top navigation toolbar.
     *
     * @return the constructed ToolBar with navigation buttons
     */
    private ToolBar createToolBar() {
        ToolBar toolBar = new ToolBar();
        toolBar.getItems().addAll(loadBtn, loginBtn, registerBtn, adminBtn, logoutBtn, exitBtn);
        return toolBar;
    }

    /**
     * Configures the button actions to switch between views using the SceneManager.
     */
    private void configureButtonActions() {
        loadBtn.setOnAction(e -> {
            SceneManager.switchTo(loadImageView.getRoot());
        });
        loginBtn.setOnAction(e -> SceneManager.switchTo(loginView.getRoot()));
        registerBtn.setOnAction(e -> SceneManager.switchTo(registerView.getRoot()));
        adminBtn.setOnAction(e -> SceneManager.switchTo(adminPanelView.getRoot()));
        logoutBtn.setOnAction(e -> {
            loginView.clearFields();
            SceneManager.switchTo(loginView.getRoot());
        });
        exitBtn.setOnAction(e -> stage.close());
    }
}