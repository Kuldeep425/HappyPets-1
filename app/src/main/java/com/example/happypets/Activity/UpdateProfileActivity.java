package com.example.happypets.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.happypets.Fragments.DatePickerFragment;
import com.example.happypets.R;
import com.google.android.material.textfield.TextInputEditText;

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
}