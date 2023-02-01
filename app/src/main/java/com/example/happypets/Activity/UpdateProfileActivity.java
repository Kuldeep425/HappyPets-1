package com.example.happypets.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.happypets.R;
import com.google.android.material.textfield.TextInputEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateProfileActivity extends AppCompatActivity {

    // defining all the variables
    CircleImageView select_image;
    TextInputEditText editText_name, editText_email, editText_phone, editText_address, editText_pincode;
    LinearLayout select_dob;
    TextView display_dob;

    // variables to store user data
    String name, email, phone, address, pincode;

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
    }
}