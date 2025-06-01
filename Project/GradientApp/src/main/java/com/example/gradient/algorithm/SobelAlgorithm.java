package com.example.gradient.algorithm;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class SobelAlgorithm implements GradientAlgorithm {

    private static final int[][] SOBEL_X = {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
    };

    private static final int[][] SOBEL_Y = {
            {-1, -2, -1},
            {0, 0, 0},
            {1, 2, 1}
    };

    /**
     * Applies the Sobel operator to the input image.
     *
     * @param input The input image.
     * @return A new image with the Sobel operator applied.
     * This method apply matrix convolution to the input image using the Sobel operator.
     * It calculates the gradient magnitude at each pixel and returns a new image.
     */
    @Override
    public Image apply(Image input) {
        int width = (int) input.getWidth();
        int height = (int) input.getHeight();

        WritableImage output = new WritableImage(width, height);
        PixelReader reader = input.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        IntStream.range(1, height - 1).parallel().forEach(y ->
                IntStream.range(1, width - 1).forEach(x -> {
                    double gx = applyKernel(reader, x, y, SOBEL_X);
                    double gy = applyKernel(reader, x, y, SOBEL_Y);
                    double magnitude = Math.min(1.0, Math.sqrt(gx * gx + gy * gy)); // Pitagora theorem
                    Color resultColor = new Color(magnitude, magnitude, magnitude, 1.0);
                    writer.setColor(x, y, resultColor);
                })
        );

        return output;
    }

    /**
     * Applies a kernel to the pixel at (x, y) in the image.
     *
     * @param reader The pixel reader for the image.
     * @param x      The x-coordinate of the pixel.
     * @param y      The y-coordinate of the pixel.
     * @param kernel The kernel to apply.
     * @return The result of applying the kernel to the pixel.
     */
    private double applyKernel(PixelReader reader, int x, int y, int[][] kernel) {
        return IntStream.range(-1, 2).boxed()
                .flatMap(dy -> IntStream.range(-1, 2)
                        .mapToObj(dx -> {
                            Color color = reader.getColor(x + dx, y + dy);
                            double gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
                            return gray * kernel[dy + 1][dx + 1];
                        }))
                .mapToDouble(Double::doubleValue)
                .sum();
    }

}
