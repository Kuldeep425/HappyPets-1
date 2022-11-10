package com.example.happypets.Activity;



import static com.example.happypets.Activity.LoginActivity.userId;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happypets.Model.Pet;
import com.example.happypets.R;
import com.example.happypets.Utils.RealPathUtil;
import com.example.happypets.Retrofit.APICall;
import com.example.happypets.Retrofit.RetrofitService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;


import java.io.File;
import java.io.IOException;


import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PetRegistrationActivity extends AppCompatActivity {

    String path;
    private CircleImageView pet_profile_image;
    private TextInputEditText registerPetName,registerPetBreed,
            registerPetAge, registerPetWeight;
    private Spinner GenderSpinner,CategorySpinner;
    private Button registerButton ;
    private Uri selectedImageUri;
    private Bitmap selectedImageBitmap;

    private ProgressDialog loader ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_registeration);

       /* backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetRegistrationActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        */

        pet_profile_image = findViewById(R.id.pet_profile_image);
        registerPetName= findViewById(R.id.registerPetName);
        registerPetBreed = findViewById(R.id.registerPetBreed);
        registerPetAge = findViewById(R.id.registerPetAge);
        registerPetWeight = findViewById(R.id.registerPetWeight);
        GenderSpinner = findViewById(R.id.GenderSpinner);
        CategorySpinner = findViewById(R.id.CategorySpinner);
        registerButton = findViewById(R.id.registerButton);
        loader = new ProgressDialog(this);



        pet_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageFromGallery();
            }
        });


        // to register a pet
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String PetName = registerPetName.getText().toString().trim();
                final String PetBreed = registerPetBreed.getText().toString().trim();
                final String PetAge = registerPetAge.getText().toString().trim();
                final String PetWeight = registerPetWeight.getText().toString().trim();
                final String Gender = GenderSpinner.getSelectedItem().toString();
                final String Category = CategorySpinner.getSelectedItem().toString();

                if(TextUtils.isEmpty(PetName)){
                    registerPetName.setError("PetName is required!");
                    return;
                }
                if(TextUtils.isEmpty(PetBreed)){
                    registerPetBreed.setError("PetBreed is required!");
                    return;
                }

                if(TextUtils.isEmpty(PetAge)){
                    registerPetAge.setError("PetAge is required!");
                    return;
                }
                if(TextUtils.isEmpty(PetWeight)){
                    registerPetWeight.setError("PetWeight is required!");
                    return;
                }

                if(Gender.equals("Select pet's gender here!!")){
                    Toast.makeText(PetRegistrationActivity.this, "Select your pet's gender!!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(selectedImageUri==null){
                    Toast.makeText(PetRegistrationActivity.this, "Please select a image", Toast.LENGTH_SHORT).show();
                    return;
                }
                Pet pet=new Pet();
                pet.setName(PetName);
                pet.setAge(PetAge);
                pet.setBreed(PetBreed);
               pet.setCategory(Category);
                pet.setGender(Gender);
                System.out.println("path: "+path);
                File image=new File(path);
                RequestBody requestFile=RequestBody.create(MediaType.parse("multipart/form-data"),image);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", image.getName(), requestFile);
                RetrofitService retrofitService = new RetrofitService();
                APICall apiCall = retrofitService.getRetrofit().create(APICall.class);

                // to post a pet
                apiCall.postAPet(userId,body,pet).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Toast.makeText(PetRegistrationActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        System.out.println(response);
                        makeEmptyAllField();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        System.out.println(t);
                        System.out.println(call);
                        Toast.makeText(PetRegistrationActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
  // to clear all fields if pet posted successfully
    private void makeEmptyAllField() {
        registerPetName.setText("");
        registerPetAge.setText("");
        registerPetWeight.setText("");
        registerPetBreed.setText("");

        pet_profile_image.setImageDrawable(getResources().getDrawable(R.drawable.profile_image));
    }


    // to choose a pet photo from gallery
    public void chooseImageFromGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(i);
    }
    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null
                            && data.getData() != null) {
                        System.out.println("In path finder");
                        selectedImageUri = data.getData();
                        Context context=PetRegistrationActivity.this;
                        path=RealPathUtil.getRealPath(context,selectedImageUri);
                        System.out.println(path);
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        pet_profile_image.setImageBitmap(selectedImageBitmap);
                    }
                }
            });
}
