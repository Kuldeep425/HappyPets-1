package com.example.happypets.Activity;

import static com.example.happypets.Activity.LoginActivity.PREFERENCE_DETAIL;
import static com.example.happypets.Activity.LoginActivity.token;
import static com.example.happypets.Activity.LoginActivity.userId;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happypets.Fragments.DatePickerFragment;
import com.example.happypets.Model.User;
import com.example.happypets.R;
import com.example.happypets.Retrofit.APICall;
import com.example.happypets.Retrofit.RetrofitService;
import com.example.happypets.Utils.RealPathUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // defining all the variables
    CircleImageView select_image;
    TextInputEditText editText_name, editText_email, editText_phone, editText_address, editText_pincode;
    LinearLayout select_dob;
    Button save_button;
    TextView display_dob;
    Uri selectedImageUri;
    Bitmap selectedImageBitmap;
    String path;
    ProgressDialog progressDialog;
    // variables to store user data
    String name, email, phone, address, pincode, dateOfBirth;

    public void openDialog(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Saving");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false); // so that it doesn't disappear when user clicks on screen
        progressDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        //changing action bar
        getSupportActionBar().setTitle("Update Profile");

        // hooking layout elements
        initializing();
        openDialogBoxOnProfileUpdationSuccessfully();
        // adding functionality to date picker button
        select_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show( getSupportFragmentManager(), "Date Picker" );
            }
        });

        // adding functionality to add profile pic on the profile image
        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageFromGallery();
            }
        });

        // method to save profile
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtaining values from edittexts
                name = editText_name.getText().toString();
                email = editText_email.getText().toString();
                phone = editText_phone.getText().toString();
                address = editText_address.getText().toString();
                pincode = editText_pincode.getText().toString();
                System.out.println(dateOfBirth);
                if(name.length()==0 || phone.length()==0 || address.length()==0 || pincode.length()==0 || dateOfBirth.length()==0){
                    Toast.makeText(UpdateProfileActivity.this, "fill all entries", Toast.LENGTH_SHORT).show();
                     return;
                }
                if(pincode.length()!=6){
                    Toast.makeText(UpdateProfileActivity.this, "pincode length must be 6", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(selectedImageUri==null){
                    Toast.makeText(UpdateProfileActivity.this, "Please select a image", Toast.LENGTH_SHORT).show();
                    return;
                }
                 User user=new User(userId,name,email,phone,address,pincode,dateOfBirth);
                File image=new File(path);
                // creating a request to send it to the cloud
                RequestBody requestFile=RequestBody.create(MediaType.parse("multipart/form-data"),image);
                //crating a multibody to sent the request file through the api end point
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", image.getName(), requestFile);

                // creating retrofit to use the api
                RetrofitService retrofitService = new RetrofitService();
                APICall apiCall = retrofitService.getRetrofit().create(APICall.class);

                //open progress dialog
                openDialog();
                apiCall.updateUser(token,body,user).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            int a=getSharedPreferences(PREFERENCE_DETAIL,MODE_PRIVATE).getInt("isProfileCompleted",0);
                            // taking this integer this will tell from where the user has come to update activity
                            /*
                              if a=0 it means user has come from the main activity else user has come from the profile activity
                             */
                            getSharedPreferences(PREFERENCE_DETAIL,MODE_PRIVATE).edit().putInt("isProfileCompleted",1).commit();
                            // dismiss the progress dialog once request is received
                            progressDialog.dismiss();
                            // make all field empty once user is successfully updated
                            makeAllfieldsEmpty();
                            // open dialog box to show to that profile is updated successfully
                            openDialogBoxOnProfileUpdationSuccessfully();
                            if(a==1) {
                                startActivity(new Intent(UpdateProfileActivity.this, ProfileActivity.class));
                                finish();
                            }
                            else {
                                startActivity(new Intent(UpdateProfileActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("Error : ","Call : "+call+" Throwable : "+t);
                        progressDialog.dismiss();
                    }
                });

            }
        });


    }


     // to make all fields empty
    private void makeAllfieldsEmpty() {
        select_image.setImageResource(R.drawable.user);
        editText_name.setText("");
        editText_address.setText("");
        editText_phone.setText("");
        editText_pincode.setText("");
        display_dob.setText("");
    }

    // to initialize the widgets
    private void initializing(){
        select_image = findViewById(R.id.update_profile_image);
        editText_name = findViewById(R.id.update_profile_name);
        editText_email = findViewById(R.id.update_profile_email);
        editText_phone = findViewById(R.id.update_profile_phone);
        editText_address = findViewById(R.id.update_profile_address);
        editText_pincode = findViewById(R.id.update_profile_pincode);
        select_dob = findViewById(R.id.update_profile_dob);
        display_dob = findViewById(R.id.update_profile_dob_display);
        save_button = findViewById(R.id.update_profile_save);
    }

    // method to show dialog box if the profile is updated successfully
    private void openDialogBoxOnProfileUpdationSuccessfully() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_box_success);
        TextView responseTextView=dialog.findViewById(R.id.response_message_textview);
        responseTextView.setText("Your profile is updated successfully.");
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        // chosen date is read
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        // obtaining the date as string
        dateOfBirth = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());
        // updating textview
        display_dob.setText(dateOfBirth);
        display_dob.setTextColor(getResources().getColor(R.color.pink_300));
    }

    // to choose a user photo from gallery
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
                        Context context=UpdateProfileActivity.this;
                        path= RealPathUtil.getRealPath(context,selectedImageUri);
                        System.out.println(path);
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        select_image.setImageBitmap(selectedImageBitmap);
                    }
                }
            });
}