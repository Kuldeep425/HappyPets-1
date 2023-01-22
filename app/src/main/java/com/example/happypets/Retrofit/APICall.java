package com.example.happypets.Retrofit;

import com.example.happypets.Model.*;


import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
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
    Call<LoginResponse> loginUser(@Body Login login);

    @GET("/get/user/{userId}")
    Call<User>getSpecificInUser(@Header ("Authorization") String token,@Path("userId") String userId);

    //get all user
    @GET("/get/all/users")
    Call<List<User>>getAllUser(@Header ("Authorization") String token);

    // to post a pet
    @POST("/post/pet/{userId}")
    @Multipart
    Call<String>postAPet(@Header ("Authorization") String token,@Path("userId") String userId, @Part MultipartBody.Part image, @Part("pet") Pet pet );

    //to get all posted pets
    @GET("/get/all/posted/pets/")
    Call<List<Pet>>getAllPostedPet(@Header ("Authorization") String token);

    //to get all posted pet of a specific user
    @GET("/get/all/posted/pet/{userId}")
    Call<List<Pet>>getAllPostedPetOfSpecificUser(@Header ("Authorization") String token,@Path("userId")String userId);


   //to get pets by category
    @GET("get/pets/category/{num}")
    Call<List<Pet>>getPetByCategory(@Header ("Authorization") String token,@Path("num") int num);

    @GET("/get/messages/{senderId}/{receiverId}")
    Call<List<ChatMessage>>getAllPreviousChats(@Header("Authorization") String token,@Path("senderId") String senderId,@Path("receiverId") String recipientId);

    @GET("/get/recently-posted/pets")
    Call<List<Pet>>getPopularPets(@Header("Authorization") String token);
}
