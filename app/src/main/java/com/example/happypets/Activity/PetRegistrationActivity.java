package com.example.happypets.Activity;

import android.os.Bundle;

        import androidx.appcompat.app.AppCompatActivity;


        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.Bundle;

        import android.provider.MediaStore;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.material.textfield.TextInputEditText;


        import java.io.ByteArrayOutputStream;
        import java.io.IOException;


        import de.hdodenhof.circleimageview.CircleImageView;


public class PetRegistrationActivity extends AppCompatActivity {
    private TextView backButton;
    private CircleImageView pet_profile_image;
    private TextInputEditText registerPetName,registerPetBreed, registerPetColor,
            registerPetAge, registerPetWeight, registerPetId , registerOwnerId;
    private Spinner GenderSpinner ;
    private Button registerButton ;
    private Uri resultUri;

    private ProgressDialog loader ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_registeration);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetRegistrationActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        pet_profile_image = findViewById(R.id.pet_profile_image);
        registerPetName= findViewById(R.id.registerPetName);
        registerPetBreed = findViewById(R.id.registerPetBreed);
        registerPetColor = findViewById(R.id.registerPetColor);
        registerPetAge = findViewById(R.id.registerPetAge);
        registerPetWeight = findViewById(R.id.registerPetWeight);
        registerPetId = findViewById(R.id.registerPetId);
        GenderSpinner = findViewById(R.id.GenderSpinner);
        registerButton = findViewById(R.id.registerButton);
        loader = new ProgressDialog(this);



        pet_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent , 1);

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String PetName = registerPetName.getText().toString().trim();
                final String PetBreed = registerPetBreed.getText().toString().trim();
                final String PetColor = registerPetColor.getText().toString().trim();
                final String PetAge = registerPetAge.getText().toString().trim();
                final String PetWeight = registerPetWeight.getText().toString().trim();
                final String PetId = registerPetId.getText().toString().trim();
                final String OwnerId = registerOwnerId.getText().toString().trim();
                final String Gender = GenderSpinner.getSelectedItem().toString();

                if(TextUtils.isEmpty(PetName)){
                    registerPetName.setError("PetName is required!");
                    return;
                }
                if(TextUtils.isEmpty(PetBreed)){
                    registerPetBreed.setError("PetBreed is required!");
                    return;
                }
                if(TextUtils.isEmpty(PetColor)){
                    registerPetColor.setError("PetColor is required!");
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
                if(TextUtils.isEmpty(PetId)){
                    registerPetId.setError("PetId is required!");
                    return;
                }
                if(TextUtils.isEmpty(OwnerId)){
                    registerOwnerId.setError("OwnerId is required!");
                    return;
                }
                if(Gender.equals("Select pet's gender here!!")){
                    Toast.makeText(PetRegistrationActivity.this, "Select your pet's gender!!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    loader.setMessage("Registering your pet.....");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                               //profile picture fetch
                                if(resultUri !=null){
                                    //final StorageReference filePath = FirebaseStorage.getInstance().getReference()
                                    //.child("profile images").child(CurrentUserId);

                                    Bitmap bitmap = null;
                                    try {
                                        bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(),resultUri);
                                    }
                                    catch(IOException e){
                                        e.printStackTrace();
                                    }
                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20,byteArrayOutputStream);
                                    byte[] data = byteArrayOutputStream.toByteArray();
                                    //UploadTask uploadTask = filePath.putBytes(data);

                                   //upload picture if failure code..



                                    //upload picture if success code..

                                    Intent intent = new Intent(PetRegistrationActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    loader.dismiss();

                                }


                            }
                        }
                    });

                }



            }
