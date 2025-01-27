package com.example.wheretogo;

public class Cafe {
    private String name;
    private String location;
    private String description;
    private String imageBase64;
    private boolean isFavorite;

    public Cafe() {}

    public Cafe(String name, String location, String description, String imageBase64) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.imageBase64 = imageBase64;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
}
