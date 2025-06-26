package com.example.gradient.database;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ImageRepositoryTest {

    @Test
    void getAllImagesReturnsArrayListTest() {
        ImageDAO imageDAO = new ImageRepository();
        ArrayList<ImageEntity> images = (ArrayList<ImageEntity>) imageDAO.getAllImages();

        assertNotNull(images, "The list must not be null");
        assertEquals(ArrayList.class, images.getClass(), "The list must be type of ArrayList");
        assertFalse(images.isEmpty(), "The list of images must not be empty ");

        for (ImageEntity image : images) {
            assertNotNull(image.getName(), "Name of image must not be null");
            assertNotNull(image.getPath(), "Path of image must not be null");
            assertNotNull(image.getDescription(), "Description of image must not be null");
            System.out.println("Image: " + image.getName() + ", Path: " + image.getPath() + ", Description: " + image.getDescription() + ", User ID: " + image.getId_user());
        }
    }

    @Test
    void getImageByIdReturnsCorrectImage() {
        ImageDAO imageDAO = new ImageRepository();

        ImageEntity testImage = new ImageEntity();
        testImage.setName("Sample");
        testImage.setPath("/images/sample.png");
        testImage.setDescription("Sample image for test");
        testImage.setId_user(1);
        imageDAO.insertImage(testImage);

        ImageEntity insertedImage = imageDAO.getAllImages().stream()
                .filter(img -> "/images/sample.png".equals(img.getPath()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Inserted image not found"));
        ImageEntity found = imageDAO.getImageById(insertedImage.getId());

        assertNotNull(found, "The image must not be null");
        assertEquals("Sample", found.getName());
        assertEquals("/images/sample.png", found.getPath());
        assertEquals("Sample image for test", found.getDescription());
        assertEquals(1, found.getId_user());
    }


    @Test
    void getImageByIdReturnsNullIfNotFound() {
        ImageDAO imageDAO = new ImageRepository();
        ImageEntity image = imageDAO.getImageById(-99);
        assertNull(image, "Should return null if no image is found with the given ID");
    }

    @Test
    void addImageSuccessfullyInserts() {
        ImageDAO imageDAO = new ImageRepository();

        ImageEntity testImage = new ImageEntity();
        testImage.setName("Test Image");
        testImage.setPath("/test/image/test.png");
        testImage.setDescription("Image for testing");
        testImage.setId_user(1);
        imageDAO.insertImage(testImage);

        List<ImageEntity> allImages = imageDAO.getAllImages();

        boolean found = allImages.stream()
                .map(ImageEntity::getPath)
                .anyMatch(path -> path.equals("/test/image/test.png"));
        assertTrue(found, "The test image should be found in the image list after insertion.");
    }


    @Test
    void updateImageSuccessfullyModifies() {
        ImageDAO imageDAO = new ImageRepository();

        ImageEntity image = new ImageEntity();
        image.setName("Original");
        image.setPath("/test/image/test.png");
        image.setDescription("Initial desc");
        image.setId_user(1);
        imageDAO.insertImage(image);

        ImageEntity insertedImage = imageDAO.getAllImages().stream()
                .filter(img -> img.getPath().equals("/test/image/test.png"))
                .findFirst()
                .orElseThrow();

        insertedImage.setName("Modified");
        insertedImage.setDescription("Updated desc");
        imageDAO.updateImage(insertedImage);
    }


    @Test
    void deleteImageRemovesCorrectly() {
        ImageDAO imageDAO = new ImageRepository();
        ImageEntity testImage = new ImageEntity();
        testImage.setName("To Be Deleted");
        testImage.setPath("/temp/delete/test-" + System.currentTimeMillis() + ".png");
        testImage.setDescription("Image used for delete test");
        testImage.setId_user(1);
        imageDAO.insertImage(testImage);

        ImageEntity insertedImage = imageDAO.getAllImages().stream()
                .filter(img -> img.getPath().equals(testImage.getPath()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Test image was not inserted correctly"));

        int insertedId = insertedImage.getId();
        System.out.println("Inserted image ID: " + insertedId);

        imageDAO.deleteImage(insertedId);
        System.out.println("Deleted image with ID: " + insertedId);

        ImageEntity deletedImage = imageDAO.getImageById(insertedId);
        assertNull(deletedImage, "Image should be deleted and not found by ID");
    }
}
