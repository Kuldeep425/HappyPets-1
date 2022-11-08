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
import retrofit2.http.Path;
import java.util.*;
public interface APICall {

    // to register a user
    @POST("/register/user")
    @Multipart
    Call<String> registerUser(@Part MultipartBody.Part image, @Part("user") User user);

    // to login user
    @POST("/login/user")
    Call<String> loginUser(@Body Login login);

    // to post a pet
    @POST("/post/pet/636a85f6dee1b6038c81f6b7")
    @Multipart
    Call<String>postAPet(@Part MultipartBody.Part image,@Part("pet") Pet pet );

    //to get all posted pets
    @GET("/get/all/posted/pets/")
    Call<List<Pet>>getAllPostedPet();


   // to get pets by category
    @GET("get/pets/category/{num}")
    Call<List<Pet>>getPetByCategory(@Path("num") int num);


}
