package com.example.happypets.Activity;

import static com.example.happypets.Activity.LoginActivity.token;
import static com.example.happypets.Activity.LoginActivity.userId;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.happypets.Model.Pet;
import com.example.happypets.Model.User;
import com.example.happypets.R;
import com.example.happypets.Retrofit.APICall;
import com.example.happypets.Retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetDetailDisplayActivity extends AppCompatActivity {

    ImageView fav_icon,chatImageView;
    int isFavourite=0;
    String ownerId,petId;
    TextView petNameTxt,petGenderTxt,petAgeTxt,petWeightTxt,petAddressTxt;
    TextView ownerNameTxt,ownerAddressTxt;
    CircleImageView ownerProfileImageview;
    ImageView petImageview;



    //Retrofit class and APICall interface
    RetrofitService retrofitService;
    APICall apiCall;

    // method to initialize the components
    private void initialize() {
        // pet
        petNameTxt=findViewById(R.id.petName);
        petGenderTxt=findViewById(R.id.petGender);
        petAddressTxt=findViewById(R.id.pet_addressText);
        petAgeTxt=findViewById(R.id.petAge);
        petWeightTxt=findViewById(R.id.petWeight);
        fav_icon=findViewById(R.id.add_fav_icon);

        //owner
        ownerNameTxt=findViewById(R.id.userNametxt);
        ownerAddressTxt=findViewById(R.id.userAddresstxt);
        ownerProfileImageview=findViewById(R.id.userProfilePic);
        petImageview=findViewById(R.id.pet_imageView);

        chatImageView=findViewById(R.id.imageViewChat);
    }

    // go to chatDetailActivity once the user click on ChatImage Icon
     public  void goToChatDetailActivity(View v){
        Intent intent=new Intent(PetDetailDisplayActivity.this,ChatDetailActivity.class);
        intent.putExtra("ownerId",ownerId);
        startActivity(intent);
     }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petdetail_display);



          // to hide the action bar
          getSupportActionBar().hide();

          // initializing the components
          initialize();

          //get the ownerId and petId from intent
           ownerId=getIntent().getStringExtra("ownerId");
           petId=getIntent().getStringExtra("petId");
           
           // initialize Retrofit and APICall
             retrofitService=new RetrofitService();
             apiCall=retrofitService.getRetrofit().create(APICall.class);

             //get the details of pet
           apiCall.getSpecificPet(token,userId,petId).enqueue(new Callback<Pet>() {
               @Override
               public void onResponse(Call<Pet> call, Response<Pet> response) {
                   if(response.isSuccessful()){
                       // set the fields of pet when response is received
                       Toast.makeText(PetDetailDisplayActivity.this, "Pet received", Toast.LENGTH_SHORT).show();
                       setAllFieldsOfPet(response.body());
                   }
               }

               @Override
               public void onFailure(Call<Pet> call, Throwable t) {
                   Log.d("Error","Call : "+call +" Throwable: "+t);
               }
           });

           // find the details of owner of pet

           apiCall.getSpecificInUser(token,ownerId).enqueue(new Callback<User>() {
               @Override
               public void onResponse(Call<User> call, Response<User> response) {
                   if(response.isSuccessful()){
                       Toast.makeText(PetDetailDisplayActivity.this, "Owner received", Toast.LENGTH_SHORT).show();
                       // set the fields of owner if the response is received
                       setAllFieldsOfOwner(response.body());
                   }
               }

               @Override
               public void onFailure(Call<User> call, Throwable t) {
                   Log.d("Error","Call : "+call +" Throwable: "+t);
               }
           });


          //on pressing the add favourite icon
           fav_icon.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   // isFavourite=true means that user has already added this pet to his favourite list
                   if(isFavourite==1){
                       //remove this pet from user's favourite list
                       removePetFromFavouriteList(userId,petId);

                   }
                   else{
                       // add this pet to user's favorite list
                       addToFavouriteList(userId,petId);
                   }
               }
           });



    }

    //method to add pet in favourite list
    private void addToFavouriteList(String userId,String petId) {

        apiCall.addToFavourite(token,userId,petId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    fav_icon.setImageResource(R.drawable.colorful_heart);
                    Toast.makeText(PetDetailDisplayActivity.this, "added", Toast.LENGTH_SHORT).show();
                    isFavourite=1;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Error","Call : "+call +" Throwable: "+t);
            }
        });
    }

    //method to remove pet from favourite list
    private void removePetFromFavouriteList(String userId, String petId) {
        apiCall.removeFromFavourite(token,userId,petId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    fav_icon.setImageResource(R.drawable.heart_add_fav);
                    isFavourite=0;
                    Toast.makeText(PetDetailDisplayActivity.this, "Removed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Error","Call : "+call +" Throwable: "+t);
            }
        });
    }

    // method to set all fields of owner
    private void setAllFieldsOfOwner(User user) {
         ownerNameTxt.setText(user.getName());
         ownerAddressTxt.setText("Ayodhya,212721");
         Picasso.get().load(user.getImageUrl()).into(ownerProfileImageview);
    }

    // method to set all fields of pet
    private void setAllFieldsOfPet(Pet pet) {
        System.out.println(pet);
        petNameTxt.setText(pet.getName());
        petAgeTxt.setText(pet.getAge()+" years");
        petGenderTxt.setText(pet.getGender());
        petWeightTxt.setText(pet.getWeight()+" kg");
        petAddressTxt.setText("Mnnit Allahabad,211004");
        Picasso.get().load(pet.getImageUrl()).into(petImageview);
        isFavourite=pet.getFav();
        System.out.println("isFav : "+isFavourite);
        if(isFavourite==1) fav_icon.setImageResource(R.drawable.colorful_heart);
    }




}
