package com.example.happypets.Model;

/*
* serializedName used to denote that the field for which this annotation is used
* is serialized with to JASON with the provided name value in annotation attribute
 */
import com.google.gson.annotations.SerializedName;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    @SerializedName("imageURL")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // this constructor is to take values from the signup page
    public User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // this constructor will take values when the user profile is being updated
    public User(String name, String email, String password, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
    public String getId(){ return id; }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
