package com.example.campogrande;

import java.util.ArrayList;

public class CampingProperites {
    //private String propRandomKey;
    private String currentDate;
    private String currentTime;
    private String propertyName;
    private String address;
    private String size;
    private String price;
    private String description;
    private String imageUrl;

    // private String id;

    public CampingProperites() {

    }

    public CampingProperites (/*String propRandomKey, */String currentDate, String currentTime, String propertyName, String address, String size, String price, String description, String imageUrl ) {
        //this.propRandomKey = propRandomKey;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.propertyName = propertyName;
        this.address = address;
        this.size = size;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }

   /* public String getPropRandomKey() {
        return propRandomKey;
    }

    public void setPropRandomKey(String propRandomKey) {
        this.propRandomKey = propRandomKey;
    }*/

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
