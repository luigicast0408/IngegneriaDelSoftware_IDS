package com.example.gradient.factory;

import com.example.gradient.algorithm.GradientAlgorithm;
import com.example.gradient.algorithm.PrewittAlgorithm;
import com.example.gradient.algorithm.RobertsAlgorithm;
import com.example.gradient.algorithm.SobelAlgorithm;

public class GradientFactory {
    public static GradientAlgorithm create(String type) {
        return switch (type.toLowerCase()) {
            case "sobel" -> new SobelAlgorithm();
            case "prewitt" -> new PrewittAlgorithm();
            case "roberts" -> new RobertsAlgorithm();
            default -> throw new IllegalArgumentException("Type of algorithm unknown: " + type);
        };
    }
}
