package com.example.gradient.database;

import com.example.gradient.database.config_conection.DBConnection;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImageRepository implements ImageDAO {

    @Override
    public void insertImage(ImageEntity image) {
        String sql = "INSERT INTO Images(name, path, description, id_users) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, image.getName());
            stmt.setString(2, image.getPath());
            stmt.setString(3, image.getDescription());
            stmt.setInt(4, image.getId_user());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateImage(ImageEntity imageEntity) {
        String sql = "UPDATE Images SET name = ?, path = ?, description = ? WHERE id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, imageEntity.getName());
            stmt.setString(2, imageEntity.getPath());
            stmt.setString(3, imageEntity.getDescription());
            stmt.setInt(4, imageEntity.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteImage(int id) {
        String sql = "DELETE FROM Images WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int result = stmt.executeUpdate();

            if (result == 0) {
                System.out.println("No image found with ID: " + id);
            } else {
                System.out.println("Image deleted successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ImageEntity getImageById(int id) {
        String sql = "SELECT * FROM Images WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToImageEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<ImageEntity> getAllImages() {
        ArrayList<ImageEntity> images = new ArrayList<>();
        String sql = "SELECT * FROM Images";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                images.add(mapResultSetToImageEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;
    }


    @Override
    public List<ImageEntity> searchImages(String name) {
        ArrayList<ImageEntity> images = new ArrayList<>();
        String sql = "SELECT * FROM Images WHERE name = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            stmt.setString(1, name);
            int result = stmt.executeUpdate();

            if (result == 0) {
                System.out.println("No image found with Name: " + name);
            } else {
                System.out.println("Image found successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;

    }

    private ImageEntity mapResultSetToImageEntity(ResultSet rs) throws SQLException {
        ImageEntity image = new ImageEntity();
        image.setId(rs.getInt("id"));
        image.setName(rs.getString("name"));
        image.setPath(rs.getString("path"));
        image.setDescription(rs.getString("description"));
        image.setId_user(rs.getInt("id_users"));
        return image;
    }
}
