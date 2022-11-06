package com.example.happypets.Retrofit;

import com.example.happypets.Model.*;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APICall {

    // to register a user
    @POST("/register/user")
    Call<String> registerUser(@Body User user);

    // to login user
    @POST("/login/user")
    Call<String> loginUser(@Body Login login);

    // to post a pet
    @POST("/post/pet/6364ff0ef192cf50a3c9f245")
    @Multipart
    Call<String>postAPet(@Part MultipartBody.Part image,
                         @Part("name") String name,
                         @Part("age") String age,
                         @Part("color") String color,
                         @Part("breed") String breed);

}
