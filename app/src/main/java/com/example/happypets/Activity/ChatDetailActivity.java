package com.example.happypets.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.happypets.R;

public class ChatDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        // this is to hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}