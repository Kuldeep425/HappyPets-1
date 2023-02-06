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
    private String address;
    private String pincode;
    private String dob;
    private int postedPet;
    private int favouritePet;

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

    public User(String id,String name, String email, String phoneNumber, String address, String pincode,String dob) {
        this.id=id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.pincode = pincode;
        this.dob=dob;
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

    public String getAddress() {
        return address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getPostedPet() {
        return postedPet;
    }

    public void setPostedPet(int postedPet) {
        this.postedPet = postedPet;
    }

    public int getFavouritePet() {
        return favouritePet;
    }

    public void setFavouritePet(int favouritePet) {
        this.favouritePet = favouritePet;
    }
}
