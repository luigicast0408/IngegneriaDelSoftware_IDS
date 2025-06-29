package com.example.gradient.core;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import com.example.gradient.util.JavaFXInitializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImageProcessorTest {

    @BeforeAll
    static void setupJavaFX(){
        JavaFXInitializer.init();
    }

    private Image createTestImage() {
        WritableImage img = new WritableImage(10, 10);
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                double shade = (x + y) / 20.0;
                img.getPixelWriter().setColor(x, y, Color.gray(shade));
            }
        }
        return img;
    }

    @Test
    void testThrowsIfInputIsNull() {
        ImageProcessor processor = new ImageProcessor("sobel", null);
        assertThrows(IllegalStateException.class, processor::process);
    }

    @Test
    void testProcessWithValidImage() {
        Image testImage = createTestImage();
        ImageProcessor processor = new ImageProcessor("sobel", testImage);
        processor.process();
        Image result = processor.getResult();

        assertNotNull(result, "Resulting image should not be null");
        assertEquals(testImage.getWidth(), result.getWidth(), "Widths should match");
        assertEquals(testImage.getHeight(), result.getHeight(), "Heights should match");
    }

    @Test
    void testAlgorithmSwitching() {
        Image testImage = createTestImage();

        ImageProcessor sobelProcessor = new ImageProcessor("sobel", testImage);
        sobelProcessor.process();
        Image sobelResult = sobelProcessor.getResult();

        Color sobelColor = sobelResult.getPixelReader().getColor(5, 5);
        assertNotEquals("Outputs from different algorithms should differ", sobelColor);
    }
}
