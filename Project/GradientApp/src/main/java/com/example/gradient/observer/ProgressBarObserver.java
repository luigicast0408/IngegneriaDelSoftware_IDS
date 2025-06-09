package com.example.gradient.observer;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

public class ProgressBarObserver implements Observer<Double> {

    private final ProgressBar progressBar;

    public ProgressBarObserver(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    /**
     * Updates the progress bar with a new progress value.
     *
     * @param value the new progress value.
     * <p>
     *     The update is wrapped in {@code Platform.runLater()} to ensure that
     *     it runs on the JavaFX Application Thread, as UI updates should be
     *     performed on the main GUI thread in JavaFX.
     * </p>
     */
    @Override
    public void update(Double value) {
        Platform.runLater(() -> progressBar.setProgress(value));
    }
}


