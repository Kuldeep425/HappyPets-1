package com.example.happypets.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.happypets.Adapters.TabLayoutAdapter;
import com.example.happypets.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting viewpager
        ViewPager viewPager = findViewById(R.id.viewpager);
        //setting adapter to viewpager
        TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(tabLayoutAdapter);
        //setting viewpager to tab layout
        TabLayout mainTab = findViewById(R.id.mainTab);
        mainTab.setupWithViewPager(viewPager);
    }
}


