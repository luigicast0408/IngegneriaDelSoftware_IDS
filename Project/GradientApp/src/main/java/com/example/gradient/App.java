package com.example.gradient;

import com.example.gradient.ui.view.MainLayout;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainLayout.startApp(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
