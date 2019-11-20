package com.example.campogrande;

import com.google.firebase.auth.FirebaseUser;

public class ProfileInformation {

        private String userId;
        private String name;
        private String age;
        private String city;
        private String intro;
        private String imageUrl;

public  ProfileInformation(){

    }



    public ProfileInformation (String userId, String name, String age, String city, String intro, String imageUrl) {
            this.userId = userId;
            this.name = name;
            this.age = age;
            this.city = city;
            this.intro = intro;
            this.imageUrl = imageUrl;
        }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
