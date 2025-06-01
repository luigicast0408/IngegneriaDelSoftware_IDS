package com.example.gradient.algorithm;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.stream.IntStream;

/**
 * Implements the Prewitt edge detection algorithm to compute the image gradient.
 * Applies horizontal and vertical Prewitt kernels to detect edges and produces
 * a grayscale output image highlighting edge magnitudes.
 * <p>
 * This implementation uses JavaFX {@link Image} and processes the image using
 * parallel streams for better performance.
 */
public class PrewittAlgorithm implements GradientAlgorithm {

    /**
     * Prewitt kernel for horizontal gradient (Gx).
     */
    private static final int[][] KERNEL_X = {
            { -1, 0, 1 },
            { -1, 0, 1 },
            { -1, 0, 1 }
    };

    /**
     * Prewitt kernel for vertical gradient (Gy).
     */
    private static final int[][] KERNEL_Y = {
            { -1, -1, -1 },
            {  0,  0,  0 },
            {  1,  1,  1 }
    };

    /**
     * Applies the Prewitt algorithm on the input image to calculate the gradient magnitude.
     * The edges are highlighted in the resulting grayscale image.
     * Borders are excluded due to the 3x3 kernel size requirement.
     *
     * @param input the input image to process
     * @return a new {@link Image} showing the gradient magnitudes (edges)
     */
    @Override
    public Image apply(Image input) {
        int width = (int) input.getWidth();
        int height = (int) input.getHeight();

        WritableImage output = new WritableImage(width, height);
        PixelReader reader = input.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        // Process all pixels excluding the border pixels (needed for kernel 3x3)
        IntStream.range(1, height - 1).parallel().forEach(y ->
                IntStream.range(1, width - 1).forEach(x -> {
                    double gx = applyKernel(reader, x, y, KERNEL_X);
                    double gy = applyKernel(reader, x, y, KERNEL_Y);
                    double magnitude = Math.min(1.0, Math.sqrt(gx * gx + gy * gy));
                    writer.setColor(x, y, new Color(magnitude, magnitude, magnitude, 1.0));
                })
        );

        return output;
    }

    /**
     * Applies a 3x3 convolution kernel to the pixel at position (x, y) in the image.
     * Converts each pixel in the kernel's neighborhood to grayscale and multiplies
     * it by the corresponding kernel value.
     *
     * @param reader the {@link PixelReader} to read pixel colors from the input image
     * @param x the x-coordinate of the central pixel where the kernel is applied
     * @param y the y-coordinate of the central pixel where the kernel is applied
     * @param kernel a 3x3 matrix representing the convolution kernel
     * @return the result of the convolution operation (sum of weighted grayscale values)
     */
    private double applyKernel(PixelReader reader, int x, int y, int[][] kernel) {
        return IntStream.range(0, 3).boxed().flatMapToDouble(j ->
                IntStream.range(0, 3).mapToDouble(i -> {
                    Color color = reader.getColor(x + i - 1, y + j - 1);
                    double gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
                    return gray * kernel[j][i];
                })
        ).reduce(0, Double::sum);
    }
}
