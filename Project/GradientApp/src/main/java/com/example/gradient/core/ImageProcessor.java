package com.example.gradient.core;

import com.example.gradient.algorithm.GradientAlgorithm;
import com.example.gradient.factory.GradientFactory;
import javafx.scene.image.Image;

public class ImageProcessor {
    private GradientAlgorithm algorithm;
    private Image inputImage;
    private Image resultImage;

    public ImageProcessor(String algorithmName, Image inputImage) {
        this.algorithm = GradientFactory.create(algorithmName);
        this.inputImage = inputImage;
    }

    public void process() {
        if (inputImage == null) {
            throw new IllegalStateException("Input image is null");
        }
        resultImage = algorithm.apply(inputImage);
    }

    public Image getResult() {
        return resultImage;
    }

    public void setAlgorithm(String algorithmName) {
        this.algorithm = GradientFactory.create(algorithmName);
    }

    public void setInputImage(Image inputImage) {
        this.inputImage = inputImage;
    }
}

