package com.example.happypets.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.happypets.Fragments.DatePickerFragment;
import com.example.happypets.R;
import com.example.happypets.Utils.RealPathUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

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

    // variables to store user data
    String name, email, phone, address, pincode, currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        // hooking layout elements
        initializing();

        // obtaining values from edittexts
        name = editText_name.getText().toString();
        email = editText_email.getText().toString();
        phone = editText_phone.getText().toString();
        address = editText_address.getText().toString();
        pincode = editText_pincode.getText().toString();

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


    }

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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        // chosen date is read
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        // obtaining the date as string
        currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());
        // updating textview
        display_dob.setText(currentDate);
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