package com.example.happypets.Activity;

import static com.example.happypets.Activity.LoginActivity.token;
import static com.example.happypets.Activity.LoginActivity.userId;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happypets.Adapters.FavouritePetListAdapter;
import com.example.happypets.Model.Pet;
import com.example.happypets.R;
import com.example.happypets.Retrofit.APICall;
import com.example.happypets.Retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouritePetListActivity extends AppCompatActivity {

    RecyclerView favPetRecylerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_pets_list);

        // initializing the favRecyclerView
        favPetRecylerView=findViewById(R.id.fav_pet_recylerView);
        favPetRecylerView.setLayoutManager(new LinearLayoutManager(this));


        //Retrofit to call api
        RetrofitService retrofitService=new RetrofitService();
        APICall apiCall=retrofitService.getRetrofit().create(APICall.class);

        // calling api to set the favourite pets in list

        apiCall.getAllFavouritePets(token,userId).enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                if(response.isSuccessful()){
                    List<Pet> pets=response.body();
                    FavouritePetListAdapter favouritePetListAdapter=new FavouritePetListAdapter(pets,FavouritePetListActivity.this);
                    favPetRecylerView.setAdapter(favouritePetListAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                Log.d("Error : ","Call: "+call+" Throwable: "+t);
            }
        });






    }
}
