package com.example.happypets.Model;

public class LoginResponse {
    private String id;
    private String token;
    private int profileCompleted;
    /*
       1->means user has already completed the profile
       0->means user has not completed the profile yet

     */
    public LoginResponse(String id, String token) {
        this.id = id;
        this.token = token;
    }

    public LoginResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(int profileCompleted) {
        this.profileCompleted = profileCompleted;
    }
}
