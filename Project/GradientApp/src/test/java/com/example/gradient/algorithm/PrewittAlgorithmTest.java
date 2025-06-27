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

public class PrewittAlgorithmTest {

    @BeforeAll
    static void initToolkit() {
        new JFXPanel();
    }

    @Test
    void testPrewittEdgeDetectionFromDatabaseImage() throws IOException {
        List<ImageEntity> images = getAllImagesFromDB();
        ImageEntity imageEntity = images.get(0);
        File imageFile = new File(imageEntity.getPath());
        assertTrue(imageFile.exists(), "Image file not found: " + imageFile.getAbsolutePath());

        Image inputImage = new Image(imageFile.toURI().toString());
        PrewittAlgorithm prewitt = new PrewittAlgorithm();
        Image result = prewitt.apply(inputImage);

        int[] maxXY = findMaxIntensityCord(result);
        int edgeX = maxXY[0], y = maxXY[1];
        int darkX = Math.max(0, edgeX - 40);
        int lightX = Math.min((int) result.getWidth() - 1, edgeX + 40);

        System.out.printf("Testing at: edgeX=%d, y=%d, darkX=%d, lightX=%d\n", edgeX, y, darkX, lightX);

        assertHighIntensity(result, edgeX, y, "Expected high edge intensity at (" + edgeX + "," + y + ")");
        assertLowIntensity(result, darkX, y, "Expected low intensity on dark side");
        assertLowIntensity(result, lightX, y, "Expected low intensity on light side");

        saveImage(result, "prewitt_image_test.png");
    }

    /**
     * This method returns the grayscale intensity of a pixel at (x, y)
     * @param image
     * @param x
     * @param y
     */
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
        double maxIntensity = 0;
        int maxX = 0, maxY = 0;

        for (int y = 0; y < (int) image.getHeight(); y++) {
            for (int x = 0; x < (int) image.getWidth(); x++) {
                double intensity = getGrayscaleIntensity(image, x, y);
                if (intensity > maxIntensity) {
                    maxIntensity = intensity;
                    maxX = x;
                    maxY = y;
                }
            }
        }

        System.out.printf("Max intensity: %.3f at (%d,%d)%n", maxIntensity, maxX, maxY);
        return new int[]{maxX, maxY};
    }

    private List<ImageEntity> getAllImagesFromDB(){
        ImageDAO dao = new ImageRepository();
        List<ImageEntity> images = dao.getAllImages();
        return images.isEmpty() ? null : images;
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
        File out = new File(filename);
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", out);
        System.out.println("Saved: " + out.getAbsolutePath());
    }
}
