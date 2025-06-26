package com.example.gradient.database;

import java.util.Objects;

public class ImageEntity {
    private int id;
    private String name;
    private String path;
    private String description;
    private int id_user;

    public ImageEntity() {
        id = 0;
        this.name = "";
        path = "";
        description = "";
        id_user = 0;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method test if an object is equal to another
     * @param object
     * @return boolean value
     */
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof ImageEntity image)) return false;
        return id == image.id &&
                id_user == image.id_user &&
                Objects.equals(name, image.name) &&
                Objects.equals(path, image.path) &&
                Objects.equals(description, image.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, path, description, id_user);
    }
}
