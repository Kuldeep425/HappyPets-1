package com.example.happypets.Activity;



import static com.example.happypets.Activity.LoginActivity.token;
import static com.example.happypets.Activity.LoginActivity.userId;

import android.app.Activity;
import android.app.Dialog;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
    private TextInputEditText registerPetName,registerPetBreed, registerPetAge, registerPetWeight, registerPetColor;
    private AutoCompleteTextView registerPetGender,registerPetType;
    private Button registerButton ;

    private Uri selectedImageUri;
    private Bitmap selectedImageBitmap;

    private ProgressDialog loader ;

    // getting values from the user
    String name, breed, age, weight, color, type, gender;



    //this object is used to handle the object which would be responsible for creating path for image
    // and also obtaining image from user input
    private ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        System.out.println("In path finder");
                        selectedImageUri = data.getData();
                        path = RealPathUtil.getRealPath(PetRegistrationActivity.this,selectedImageUri);
                        System.out.println(path);
                        try {
                            selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        pet_profile_image.setImageBitmap(selectedImageBitmap);
                    }
                }
            });


    @Override
    protected void onResume() {
        super.onResume();
        //obtaining list of options for drop down menu
        String[] pets_type = getResources().getStringArray(R.array.pets_menu_type);
        String[] pets_gender = getResources().getStringArray(R.array.pets_menu_gender);
        // creating array adapters
        ArrayAdapter<String> type_adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.pets_registration_drop_down_item, pets_type);
        ArrayAdapter<String> gender_adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.pets_registration_drop_down_item, pets_gender);
        //setting Array Adapters
        registerPetGender.setAdapter(gender_adapter);
        registerPetType.setAdapter(type_adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pet_registration_new);

        // hooking various layouts to java
        pet_profile_image = findViewById(R.id.pet_profile_image);
        registerPetName= findViewById(R.id.register_pet_name);
        registerPetBreed = findViewById(R.id.register_pet_breed);
        registerPetAge = findViewById(R.id.register_pet_age);
        registerPetWeight = findViewById(R.id.register_pet_weight);
        registerPetColor = findViewById(R.id.register_pet_color);
        registerPetGender = findViewById(R.id.register_pet_gender);
        registerPetType = findViewById(R.id.register_pet_type);
        registerButton = findViewById(R.id.register_pet_save);

        //creating object for the progress loader
        loader = new ProgressDialog(this);

        //obtaining list of options for drop down menu
        String[] pets_type = getResources().getStringArray(R.array.pets_menu_type);
        String[] pets_gender = getResources().getStringArray(R.array.pets_menu_gender);
        // creating array adapters
        ArrayAdapter<String> type_adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.pets_registration_drop_down_item, pets_type);
        ArrayAdapter<String> gender_adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.pets_registration_drop_down_item, pets_gender);
        // setting array adapter
        registerPetGender.setAdapter(gender_adapter);
        registerPetType.setAdapter(type_adapter);


        // setting functionality of the register button
        pet_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageFromGallery();
            }
        });

        //obtaining selected item
        registerPetGender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
                System.out.println("Gender: "+gender);
            }
        });

        registerPetType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();
                System.out.println("type: " +type);
            }
        });

        // to register a pet
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = registerPetName.getText().toString().trim();
                breed = registerPetBreed.getText().toString().trim();
                age = registerPetAge.getText().toString().trim();
                weight = registerPetWeight.getText().toString().trim();
                color = registerPetColor.getText().toString().trim();

                // checking if all the entered inputs are correct
                boolean correctFields = true;
                if(name.length()==0 || breed.length()==0 || age.length()==0 || weight.length()==0 || type==null || gender==null) correctFields=false;

                // if fields not correct then show toast message
                if(!correctFields) Toast.makeText(PetRegistrationActivity.this,"Fill all fields",Toast.LENGTH_SHORT).show();
                if(selectedImageUri==null){
                    Toast.makeText(PetRegistrationActivity.this, "Please select a image", Toast.LENGTH_SHORT).show();
                    return;
                }
                System.out.println("type: " +type);
                System.out.println("gender : " + gender);
                // creating a pet object to send it through the api end point
                Pet pet = new Pet(name,type,gender,breed,age,weight,color);

                System.out.println("path: "+path);

                // creating a file containing the path for the image that we are sending
                File image=new File(path);
                // creating a request to send it to the cloud
                RequestBody requestFile=RequestBody.create(MediaType.parse("multipart/form-data"),image);
                //crating a multibody to sent the request file through the api end point
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", image.getName(), requestFile);

                // creating retrofit to use the api
                RetrofitService retrofitService = new RetrofitService();
                APICall apiCall = retrofitService.getRetrofit().create(APICall.class);

                /*once it starts to call api we need to open progressdialog so that user can't
                     press save btn again and again ..until the previous posting request does not respond.
                 */

                //opening progress dialog
                openProgressDialog();

               //  to post a pet
                apiCall.postAPet(token,userId,body,pet).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            //once pet posted successfully,dismiss the progress dialog
                            loader.dismiss();
                            makeEmptyAllField();
                            // open dialog box to show the user that his/her pet is posted successfully
                            openDialogBoxOnPetPostedSuccessfully();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.out.println(call);
                        // although the pet does not posted successfully,stop the progress dialog and let him know
                        loader.dismiss();
                        Toast.makeText(PetRegistrationActivity.this, "Error in posting", Toast.LENGTH_SHORT).show();

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
        registerPetColor.setText("");
        registerPetGender.setText("");
        registerPetType.setText("");
        pet_profile_image.setImageDrawable(getResources().getDrawable(R.drawable.profile_image));
    }


    // to choose a pet photo from gallery
    public void chooseImageFromGallery() {
        // creating an intent to open the picture selecting dialog and obtaining value from it
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // launchSomeActivity is an object which is used to open intent and obtain value in the form of bitmap
        launchSomeActivity.launch(intent);
    }

    // opening progressdiallog to restrict user to click other btn until the request does not complete.
     public void openProgressDialog(){
         loader=new ProgressDialog(this);
         loader.setCancelable(false);
         loader.setTitle("Posting your pet");
         loader.setMessage("Please wait....");
         loader.show();
     }

     // method to show dialog on completion of pet registration
     private void openDialogBoxOnPetPostedSuccessfully() {
         Dialog dialog = new Dialog(this);
         dialog.setContentView(R.layout.dialog_box_success);
         TextView responseMessageTextview=dialog.findViewById(R.id.response_message_textview);
         responseMessageTextview.setText("Pet has been posted successfully");
         dialog.show();
    }

}
