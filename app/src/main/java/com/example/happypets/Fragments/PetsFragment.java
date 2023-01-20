package com.example.happypets.Fragments;

import static com.example.happypets.Activity.LoginActivity.token;
import static com.example.happypets.Activity.LoginActivity.userId;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.happypets.Activity.PetRegistrationActivity;
import com.example.happypets.Adapters.UserPetsListAdapter;
import com.example.happypets.Model.Pet;
import com.example.happypets.R;
import com.example.happypets.Retrofit.APICall;
import com.example.happypets.Retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetsFragment extends Fragment {

    private final String TAG = PetsFragment.class.getSimpleName();
    APICall apiCall;
    RetrofitService retrofitService;
    RecyclerView userPetRecyclerView;
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

        //initialising for calling api
        retrofitService=new RetrofitService();
        apiCall=retrofitService.getRetrofit().create(APICall.class);
        userPetRecyclerView=rootView.findViewById(R.id.user_pets_recycler_view);
        userPetRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // calling api to display posted pets by loggedIn user
         loadUserPostedPet();


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

    private void loadUserPostedPet() {
        apiCall.getAllPostedPetOfSpecificUser(token,userId).enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), ""+response.body(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body()==null){
                    Toast.makeText(getContext(), "user not found", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Pet>petList=response.body();
                UserPetsListAdapter userPetsListAdapter=new UserPetsListAdapter(petList);
                userPetRecyclerView.setAdapter(userPetsListAdapter);
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {

            }
        });
    }
}