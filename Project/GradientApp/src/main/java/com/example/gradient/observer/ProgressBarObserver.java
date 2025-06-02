package com.example.gradient.observer;

import javafx.scene.control.ProgressBar;

public class ProgressBarObserver implements Observer {
    private final ProgressBar progressBar;

    public ProgressBarObserver(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public void update(double value) {
        progressBar.setProgress(value);
    }
}
