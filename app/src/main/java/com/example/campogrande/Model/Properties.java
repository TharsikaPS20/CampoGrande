package com.example.campogrande.Model;

public class Properties {

    private String propertyName, description, price, size, imageUrl;

    public Properties()
    {

    }

    public Properties(String propertyName, String description, String price, String size, String imageUrl) {
        this.propertyName = propertyName;
        this.description = description;
        this.price = price;
        this.size = size;
        this.imageUrl = imageUrl;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
