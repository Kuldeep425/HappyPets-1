package com.example.happypets.Model;

import com.google.gson.annotations.SerializedName;

public class Pet {

    private String id;
    private String name;
    private String breed;
    private String gender;
    private String age;
    private String color;
    private String weight;
    private String category;
    private String imageURL;
    private String ownerId;
    private int fav;

    public Pet(){

    }

    public Pet(String name, String category, String gender, String breed, String age, String weight, String color){
        this.name = name;
        this.category = category;
        this.gender = gender;
        this.breed = breed;
        this.age = age;
        this.weight = weight;
        this.color = color;
    }

    public Pet(String name, String breed, String gender, String category, String imageURL,  String ownerId, String age, String color) {
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.category = category;
        this.imageURL = imageURL;
        this.id = id;
        this.ownerId = ownerId;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageURL;
    }

    public void setImageUrl(String imageUrl) {
        this.imageURL = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setWeight(String weight){this.weight=weight;}

    public String getWeight(){return weight;}


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }
}
