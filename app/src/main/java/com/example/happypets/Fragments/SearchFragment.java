package com.example.happypets.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.happypets.Activity.AllPetsListActivity;
import com.example.happypets.R;


public class SearchFragment extends Fragment {

    private final String TAG = SearchFragment.class.getSimpleName();

    // fragment view variable
    private View rootview;

    //variables to attach layout
    private CardView dog_card_view;
    private CardView cat_card_view;
    private CardView fish_card_view;
    private CardView rabbit_card_view;
    private CardView bird_card_view;
    private CardView others_card_view;

    // variable to know which option selected
    private int cardOptionSelected;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_search, container, false);

        // attaching layout to variable
        dog_card_view = (CardView) rootview.findViewById(R.id.search_card_view_dog);
        cat_card_view = (CardView) rootview.findViewById(R.id.search_card_view_cat);
        fish_card_view = (CardView) rootview.findViewById(R.id.search_card_view_fish);
        rabbit_card_view = (CardView) rootview.findViewById(R.id.search_card_view_rabbit);
        bird_card_view = (CardView) rootview.findViewById(R.id.search_card_view_bird);
        others_card_view = (CardView) rootview.findViewById(R.id.search_card_view_others);

        // all click listeners
        dog_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardOptionSelected = 0;
                startIntent(cardOptionSelected);
            }
        });
        cat_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardOptionSelected = 1;
                startIntent(cardOptionSelected);
            }
        });
        fish_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardOptionSelected = 2;
                startIntent(cardOptionSelected);
            }
        });
        rabbit_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardOptionSelected = 3;
                startIntent(cardOptionSelected);
            }
        });
        bird_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardOptionSelected = 4;
                startIntent(cardOptionSelected);
            }
        });
        others_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardOptionSelected = 5;
                startIntent(cardOptionSelected);
            }
        });



        return rootview;
    }

    private void startIntent(int option){
        Intent optionIntent = new Intent(getActivity(),AllPetsListActivity.class);
        optionIntent.putExtra("SelectedValue",option);
        Log.e(TAG,String.valueOf(option));
        startActivity(optionIntent);
    }

}