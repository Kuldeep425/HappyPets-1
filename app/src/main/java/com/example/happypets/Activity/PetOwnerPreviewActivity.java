package com.example.happypets.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.happypets.Adapters.PreviewTabLayout;
import com.example.happypets.Adapters.TabLayoutAdapter;
import com.example.happypets.R;
import com.google.android.material.tabs.TabLayout;

public class PetOwnerPreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_owner_preview);

        //setting viewpager
        ViewPager viewPager = findViewById(R.id.preview_viewpager);
        //setting adapter to viewpager
        PreviewTabLayout previewTabLayout = new PreviewTabLayout(getSupportFragmentManager(), this);
        viewPager.setAdapter(previewTabLayout);
        //setting viewpager to tab layout
        TabLayout mainTab = findViewById(R.id.preview_tab_layout);
        mainTab.setupWithViewPager(viewPager);
    }
}