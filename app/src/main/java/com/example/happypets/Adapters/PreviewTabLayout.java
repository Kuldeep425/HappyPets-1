package com.example.happypets.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.happypets.Fragments.InfoFragment;
import com.example.happypets.Fragments.OwnerPreviewFragment;
import com.example.happypets.Fragments.PetPreviewFragment;
import com.example.happypets.Fragments.PetsFragment;
import com.example.happypets.Fragments.SearchFragment;
import com.example.happypets.R;

public class PreviewTabLayout extends FragmentPagerAdapter {

    private Context context;

    public PreviewTabLayout(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new PetPreviewFragment();
            default:
                return new OwnerPreviewFragment();

        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getString(R.string.pets_preview);
            default:
                return context.getString(R.string.owner_preview);

        }
    }
}
