package com.example.happypets.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.happypets.Activity.PetRegistrationActivity;
import com.example.happypets.R;

public class PetsFragment extends Fragment {

    private final String TAG = PetsFragment.class.getSimpleName();

    // creating global variable for fragment view
    private View rootView;

    //variables to access different xml components
    private Button addPetsButton;

    public PetsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_pets, container, false);

        //hooking button
        addPetsButton = rootView.findViewById(R.id.add_pets_button);
        // attaching listener to the button
        addPetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Pressed add pets button");
                Intent addPetsIntent = new Intent(getActivity(), PetRegistrationActivity.class);
                startActivity(addPetsIntent);
            }
        });



        return rootView;
    }
}