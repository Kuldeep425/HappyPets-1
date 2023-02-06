package com.example.happypets.Activity;

import static com.example.happypets.Activity.LoginActivity.token;
import static com.example.happypets.Activity.LoginActivity.userId;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happypets.Model.User;
import com.example.happypets.R;
import com.example.happypets.Retrofit.APICall;
import com.example.happypets.Retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

     CircleImageView profileImageView;
     TextView name,email,phoneNumber,dob,address,pincode,addedPetNumber,favPetNumber;

     Button updateProfileButton;
     private void initialize(){
         profileImageView=findViewById(R.id.user_image);
         name=findViewById(R.id.user_name);
         email=findViewById(R.id.user_email);
         phoneNumber=findViewById(R.id.user_phone);
         dob = findViewById(R.id.user_dob);
         address = findViewById(R.id.user_address);
         pincode = findViewById(R.id.user_pincode);
         updateProfileButton = findViewById(R.id.user_update_profile_button);
         addedPetNumber=findViewById(R.id.profile_no_pets_added);
         favPetNumber=findViewById(R.id.profile_no_pets_fav);
     }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_new);
        initialize();

        // hiding the action bar
        getSupportActionBar().hide();

        // retrofit service
        RetrofitService retrofitService=new RetrofitService();
        APICall apiCall=retrofitService.getRetrofit().create(APICall.class);
        // to get logged in user
        apiCall.getSpecificInUser(token,userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body()!=null){
                    User user=response.body();
                    System.out.println(user.getImageUrl());
                    Picasso.get().load(user.getImageUrl()).into(profileImageView);
                    name.setText(user.getName());
                    email.setText(user.getEmail());
                    phoneNumber.setText(user.getPhoneNumber());
                    dob.setText(user.getDob());
                    address.setText(user.getAddress());
                    pincode.setText(user.getPincode());
                    addedPetNumber.setText(user.getPostedPet()+"");
                    favPetNumber.setText(user.getFavouritePet()+"");
                }
                else
                    Toast.makeText(ProfileActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(call);
                System.out.println(t);
                Toast.makeText(ProfileActivity.this, "error in loading profile", Toast.LENGTH_SHORT).show();
            }
        });

        //adding functionality of update profile button
        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpdateProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}