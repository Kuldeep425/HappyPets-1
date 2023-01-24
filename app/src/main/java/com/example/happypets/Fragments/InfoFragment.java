package com.example.happypets.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.happypets.Activity.PetOwnerPreviewActivity;
import com.example.happypets.R;

public class InfoFragment extends Fragment {

    private View rootView;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.info_tab);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_info, container, false);

        getActivity().setTitle(R.string.info_tab);


        return rootView;

    }
}