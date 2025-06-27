package com.example.gradient.observer;

import com.example.gradient.database.ImageDAO;
import com.example.gradient.database.ImageEntity;
import com.example.gradient.database.ImageRepository;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ImageListObserverTest {

    @BeforeAll
    static void setupEnvironment() throws Exception {
        initJavaFXPlatform();
    }

    @Test
    void shouldUpdateObservableListWithImagesFromDatabase() throws Exception {
        List<ImageEntity> imagesFromDb = fetchAndValidateImagesFromDatabase();

        ObservableList<ImageEntity> observableList = FXCollections.observableArrayList();
        ImageListObserver observer = new ImageListObserver(observableList);
        runOnFxThreadAndWait(() -> observer.update(imagesFromDb));
        assertImageListsEqual(imagesFromDb, observableList);
    }

    private List<ImageEntity> fetchAndValidateImagesFromDatabase() {
        List<ImageEntity> images = fetchImagesFromDatabase();
        assertNotNull(images, "Images from DB must not be null");
        assertFalse(images.isEmpty(), "There must be at least one image in the DB for this test");
        return images;
    }

    private void assertImageListsEqual(List<ImageEntity> expected, List<ImageEntity> actual) {
        assertEquals(expected.size(), actual.size(), "List size mismatch");
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getName(), actual.get(i).getName(), "Image name mismatch at index " + i);
            assertEquals(expected.get(i).getPath(), actual.get(i).getPath(), "Image path mismatch at index " + i);
        }
    }

    /**
     * Initializes the JavaFX platform for tests without GUI support.
     * <p>
     * JavaFX runs UI code on a separate thread (the JavaFX Application Thread).
     * This method starts that thread via {@code Platform#startup(Runnable)} and uses
     * {@code CountDownLatch} to block the test thread until platform initialization completes,
     * avoiding "Toolkit not initialized" errors when using JavaFX APIs.
     * </p>
     *
     * @throws  Exception if initialization doesn't complete within 5 seconds
     */
    private static void initJavaFXPlatform() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new IllegalStateException("JavaFX Platform failed to initialize");
        }
    }


    private static List<ImageEntity> fetchImagesFromDatabase() {
        ImageDAO imageDAO = new ImageRepository();
        return imageDAO.getAllImages();
    }

    private void runOnFxThreadAndWait(Runnable action) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            action.run();
            Platform.runLater(latch::countDown);
        });
        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Timeout waiting for JavaFX action to complete");
        }
    }
}
