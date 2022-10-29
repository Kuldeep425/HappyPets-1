package com.example.happypets.Retrofit;

import com.example.happypets.Model.*;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface APICall {

    // to register an user
    @POST("/register/user")
    Call<String> registerUser(@Body User user);

    // to login user
   @POST("/login/user")
    Call<String> loginUser(@Body Login login);


}
