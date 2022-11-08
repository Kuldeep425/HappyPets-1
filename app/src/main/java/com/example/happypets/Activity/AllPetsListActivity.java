package com.example.happypets.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.happypets.R;

import org.w3c.dom.Text;

public class AllPetsListActivity extends AppCompatActivity {

    private final String TAG = AllPetsListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pets_list);

        //receiving values from intent
        String value = getIntent().getExtras().getString("SelectedValue");

        Log.e(TAG,value);

        TextView textView = findViewById(R.id.text_view);
        textView.setText(value);
    }
}