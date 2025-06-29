package com.example.gradient.observer;

import com.example.gradient.util.JavaFXInitializer;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ProgressBar;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public  class ProgressBarObserverTest {

    private ProgressBar progressBar;
    private ProgressBarObserver observer;

    @BeforeAll
    static void setupJavaFX(){
        JavaFXInitializer.init();
    }

    @BeforeEach
    void setUp() {
        progressBar = new ProgressBar();
        observer = new ProgressBarObserver(progressBar);
    }

    @Test
    void testProgressUpdate() throws InterruptedException {
        double progress = 0.75;
        observer.update(progress);
        Thread.sleep(200);
        Platform.runLater(() -> assertEquals(progress, progressBar.getProgress(), 0.01));
    }
}
