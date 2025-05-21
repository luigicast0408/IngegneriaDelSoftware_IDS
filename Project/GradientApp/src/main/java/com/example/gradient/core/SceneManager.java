package com.example.gradient.core;

import javafx.scene.layout.Pane;

public class SceneManager {
    private static Pane mainContainer;

    public static void setMainContainer(Pane container) {
        mainContainer = container;
    }

    public static void switchTo(Pane newView) {
        if (mainContainer != null) {
            mainContainer.getChildren().clear();
            mainContainer.getChildren().add(newView);
        }
    }
}
