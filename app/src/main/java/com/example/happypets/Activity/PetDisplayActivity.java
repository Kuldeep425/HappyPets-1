package com.example.happypets.Activity;

import static com.example.happypets.Activity.LoginActivity.token;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.happypets.Adapters.PetAdapter;
import com.example.happypets.Model.Pet;
import com.example.happypets.R;
import com.example.happypets.Retrofit.APICall;
import com.example.happypets.Retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetDisplayActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_display);
        recyclerView=findViewById(R.id.petDisplayRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RetrofitService retrofitService=new RetrofitService();
        APICall apiCall=retrofitService.getRetrofit().create(APICall.class);
        apiCall.getAllPostedPet(token).enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(PetDisplayActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                System.out.println(response.body());
                List<Pet>petList=
                 response.body();
                PetAdapter petAdapter=new PetAdapter(PetDisplayActivity.this,petList);
                recyclerView.setAdapter(petAdapter);
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                System.out.println(call.toString());
                System.out.println(t);
                System.out.println("error");
                Toast.makeText(PetDisplayActivity.this, call.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}