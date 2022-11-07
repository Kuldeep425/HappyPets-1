package com.example.happypets;

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
    private TextInputEditText registerPetName,registerPetBreed,
            registerPetAge, registerPetWeight ;
    private Spinner GenderSpinner,CategorySpinner ;
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

        registerPetAge = findViewById(R.id.registerPetAge);
        registerPetWeight = findViewById(R.id.registerPetWeight);

        GenderSpinner = findViewById(R.id.GenderSpinner);
        CategorySpinner = findViewById(R.id.CategorySpinner);
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
                if(Category.equals("Select pet's category here!!")){
                    Toast.makeText(PetRegistrationActivity.this, "Select your pet's Category!!",Toast.LENGTH_SHORT).show();
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
