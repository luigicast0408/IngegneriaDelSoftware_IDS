package com.example.gradient.algorithm;

import com.example.gradient.database.ImageDAO;
import com.example.gradient.database.ImageRepository;
import com.example.gradient.database.ImageEntity;
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

public class RobertsAlgorithmTest {

    @BeforeAll
    static void initToolkit() {
        new JFXPanel();
    }

    @Test
    void testRobertsDetectsEdgeFromDatabaseImage() throws IOException {
        List<ImageEntity> images = getAllImagesFromDB();
        ImageEntity imageEntity = images.get(0);
        File imageFile = new File(imageEntity.getPath());
        assertTrue(imageFile.exists(), "Image file not found: " + imageFile.getAbsolutePath());

        Image inputImage = new Image(imageFile.toURI().toString());
        RobertsAlgorithm algorithm = new RobertsAlgorithm();
        Image result = algorithm.apply(inputImage);

        int[] edgeCord = findMaxIntensityCord(result);
        int edgeX = edgeCord[0];
        int y = edgeCord[1];

        int imageWidth = (int) result.getWidth();
        int darkX = edgeX / 2;
        int lightX = Math.min(edgeX + edgeX / 2, imageWidth - 1);

        System.out.printf("Testing at: edgeX=%d, y=%d, darkX=%d, lightX=%d%n", edgeX, y, darkX, lightX);

        assertEdgeIntensityHigh(result, edgeX, y);
        assertEdgeIntensityLow(result, darkX, y);
        assertEdgeIntensityLow(result, lightX, y);

        saveImage(result, "roberts_image_test.png");
    }

    private List<ImageEntity> getAllImagesFromDB(){
        ImageDAO dao = new ImageRepository();
        List<ImageEntity> images = dao.getAllImages();
        return images.isEmpty() ? null : images;
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

    private double getGrayscaleIntensity(Image image, int x, int y) {
        Color color = image.getPixelReader().getColor(x, y);
        return (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
    }

    private void assertEdgeIntensityHigh(Image image, int x, int y) {
        double intensity = getGrayscaleIntensity(image, x, y);
        System.out.printf("High intensity at (%d,%d): %.3f%n", x, y, intensity);
        assertTrue(intensity > 0.5, "Expected high edge intensity at (" + x + "," + y + ")");
    }

    private void assertEdgeIntensityLow(Image image, int x, int y) {
        double intensity = getGrayscaleIntensity(image, x, y);
        System.out.printf("Low intensity at (%d,%d): %.3f%n", x, y, intensity);
        assertTrue(intensity < 0.2, "Expected low intensity at (" + x + "," + y + ")");
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
