package com.example.happypets.Activity;

import static com.example.happypets.Activity.LoginActivity.token;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happypets.Adapters.AllPetsListAdapter;
import com.example.happypets.Model.Pet;
import com.example.happypets.R;
import com.example.happypets.Retrofit.APICall;
import com.example.happypets.Retrofit.RetrofitService;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllPetsListActivity extends AppCompatActivity {

    private final String TAG = AllPetsListActivity.class.getSimpleName();

    private List<Pet> petListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pets_list);

        //receiving values from intent
        int value = getIntent().getExtras().getInt("SelectedValue");
        Toast.makeText(this, ""+value, Toast.LENGTH_SHORT).show();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.all_pets_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RetrofitService retrofitService=new RetrofitService();
        APICall apiCall=retrofitService.getRetrofit().create(APICall.class);
        apiCall.getPetByCategory(token,value).enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                if(!response.isSuccessful()){
                    System.out.println("error in response");
                    return;
                }
                petListData=response.body();
                AllPetsListAdapter adapter = new AllPetsListAdapter(petListData,AllPetsListActivity.this);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                System.out.println(call);
                System.out.println(t);
            }
        });



    }
}