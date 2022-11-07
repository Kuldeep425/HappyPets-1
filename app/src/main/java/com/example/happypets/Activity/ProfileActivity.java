package com.example.happypets.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.happypets.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initialize();
    }

    private void initialize() {
    }
}