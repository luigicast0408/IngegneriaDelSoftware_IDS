package com.example.gradient.algorithm;

import com.example.gradient.database.ImageDAO;
import com.example.gradient.database.ImageEntity;
import com.example.gradient.database.ImageRepository;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SobelAlgorithmTest {

    @BeforeAll
    static void initToolkit() {
        new JFXPanel();
    }

    @Test
    void testSobelDetectsEdgeFromDatabaseImage() throws IOException {
        List<ImageEntity> images = getAllImagesFromDB();
        ImageEntity imageEntity = images.get(0);
        File imageFile = new File(imageEntity.getPath());
        assertTrue(imageFile.exists(), "Image file not found: " + imageFile.getAbsolutePath());


        Image inputImage = new Image(imageFile.toURI().toString());
        SobelAlgorithm sobel = new SobelAlgorithm();
        Image result = sobel.apply(inputImage);

        int[] maxXY = findMaxIntensityCord(result);
        int edgeX = maxXY[0], y = maxXY[1];
        int darkX = Math.max(0, edgeX - 40);
        int lightX = Math.min((int) result.getWidth() - 1, edgeX + 40);
        System.out.printf("Testing at: edgeX=%d, y=%d, darkX=%d, lightX=%d\n", edgeX, y, darkX, lightX);

        assertHighIntensity(result, edgeX, y, "Expected strong edge intensity at (" + edgeX + "," + y + ")");
        assertLowIntensity(result, darkX, y, "Expected low intensity in dark region");
        assertLowIntensity(result, lightX, y, "Expected low intensity in light region");
        saveImage(result, "sobel_image_test.png");
    }

    private List<ImageEntity> getAllImagesFromDB(){
        ImageDAO dao = new ImageRepository();
        List<ImageEntity> images = dao.getAllImages();
        return images.isEmpty() ? null : images;
    }

    private double getGrayscaleIntensity(Image image, int x, int y) {
        Color color = image.getPixelReader().getColor(x, y);
        return (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
    }

    /**
     * Finds the coordinates (x, y) of the pixel with the highest grayscale intensity in the entire image.
     *
     * @param image the input image to analyze
     * @return an array containing two values: [maxX, maxY] of the pixel with maximum intensity
     */
    private int[] findMaxIntensityCord(Image image) {
        double max = 0;
        int maxX = 0, maxY = 0;

        for (int y = 0; y < (int) image.getHeight(); y++) {
            for (int x = 0; x < (int) image.getWidth(); x++) {
                double intensity = getGrayscaleIntensity(image, x, y);
                if (intensity > max) {
                    max = intensity;
                    maxX = x;
                    maxY = y;
                }
            }
        }

        System.out.printf("Max intensity: %.3f at (%d,%d)%n", max, maxX, maxY);
        return new int[]{maxX, maxY};
    }

    private void assertHighIntensity(Image image, int x, int y, String message) {
        double intensity = getGrayscaleIntensity(image, x, y);
        assertTrue(intensity > 0.5, message + " => found: " + intensity);
    }

    private void assertLowIntensity(Image image, int x, int y, String message) {
        double intensity = getGrayscaleIntensity(image, x, y);
        assertTrue(intensity < 0.2, message + " => found: " + intensity);
    }

    /**
     * Saves a JavaFX {@link javafx.scene.image.Image} to a file in PNG format.
     * Internally converts the JavaFX {@code Image} to a {@code BufferedImage} before saving.
     *
     * @param image
     * @param filename
     * @throws IOException
     */

    private void saveImage(Image image, String filename) throws IOException {
        File output = new File(filename);
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", output);
        System.out.println("Saved image to: " + output.getAbsolutePath());
    }
}
