package com.example.gradient.util;

import javafx.embed.swing.JFXPanel;

public final class JavaFXInitializer {
    private static boolean initialized = false;

    private JavaFXInitializer() {}

    public static synchronized void init() {
        if (!initialized) {
            new JFXPanel(); // triggers JavaFX
            initialized = true;
        }
    }
}