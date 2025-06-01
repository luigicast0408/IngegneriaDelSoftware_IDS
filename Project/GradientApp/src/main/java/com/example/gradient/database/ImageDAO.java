package com.example.gradient.database;

import java.awt.*;
import java.util.List;

public interface ImageDAO {
    void insertImage(ImageEntity imageEntity);

    void updateImage(ImageEntity imageEntity);

    void deleteImage(int id);

    ImageEntity getImageById(int id);

    List<ImageEntity> getAllImages();

    List<ImageEntity> searchImages(String query);
}
