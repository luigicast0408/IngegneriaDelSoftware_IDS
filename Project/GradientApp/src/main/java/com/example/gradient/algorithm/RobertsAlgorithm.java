package com.example.gradient.algorithm;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.stream.IntStream;

public class RobertsAlgorithm implements GradientAlgorithm {

    @Override
    public Image apply(Image input) {
        int width = (int) input.getWidth();
        int height = (int) input.getHeight();

        WritableImage output = new WritableImage(width, height);
        PixelReader reader = input.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        IntStream.range(0, height - 1).parallel().forEach(y ->
                IntStream.range(0, width - 1).forEach(x -> {
                    double gx = getGray(reader, x, y) - getGray(reader, x + 1, y + 1);
                    double gy = getGray(reader, x + 1, y) - getGray(reader, x, y + 1);
                    double magnitude = Math.min(1.0, Math.sqrt(gx * gx + gy * gy));
                    writer.setColor(x, y, new Color(magnitude, magnitude, magnitude, 1.0));
                })
        );

        return output;
    }

    private double getGray(PixelReader reader, int x, int y) {
        Color color = reader.getColor(x, y);
        return (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
    }
}
