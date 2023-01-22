package com.example.happypets.Fragments;

import static com.example.happypets.Activity.LoginActivity.token;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.happypets.Activity.AllPetsListActivity;
import com.example.happypets.Adapters.CategoryAdapter;
import com.example.happypets.Adapters.PopularAdapter;
import com.example.happypets.Model.Category;
import com.example.happypets.Model.Pet;
import com.example.happypets.R;
import com.example.happypets.Retrofit.APICall;
import com.example.happypets.Retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {

    private final String TAG = SearchFragment.class.getSimpleName();

    // fragment view variable
    private View rootview;
    private RecyclerView recyclerViewCategoryList;
    private  RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewPopularList;
    private  RecyclerView.Adapter popularAdapter;
    private List<Pet>pets;
    ImageView imageView;
    EditText searchEditText;


    // variable to know which option selected
    private int cardOptionSelected;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_search, container, false);
          recyclerViewCategory();
          recyclerViewPopular();

        return rootview;
    }

    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopularList=rootview.findViewById(R.id.recyclerView_popular);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        RetrofitService retrofitService=new RetrofitService();
        APICall apiCall=retrofitService.getRetrofit().create(APICall.class);

        apiCall.getPopularPets(token).enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                if(response.isSuccessful()){
                    pets=response.body();
                    popularAdapter=new PopularAdapter(pets,getContext());
                    recyclerViewPopularList.setAdapter(popularAdapter);
                }
                else System.out.println("did not get response");
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                System.out.println(t);
                System.out.println("fdjfhdkjghdfhg iudfhgdf g");
            }
        });

    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategoryList=rootview.findViewById(R.id.recyclerView_category);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);
        ArrayList<Category>categories=new ArrayList<>();

        categories.add(new Category("Dog","cat_pic1"));
        categories.add(new Category("Cat","cat_pic2"));
        categories.add(new Category("Horse","cat_pic3"));
        categories.add(new Category("Bird","cat_pic4"));
        categories.add(new Category("Fish","cat_pic5"));
        categories.add(new Category("Rabbit","cat_pic6"));

        adapter=new CategoryAdapter(categories,getContext());
        recyclerViewCategoryList.setAdapter(adapter);


    }

    private void startIntent(int option){
        Intent optionIntent = new Intent(getActivity(),AllPetsListActivity.class);
        optionIntent.putExtra("SelectedValue",option);
        Log.e(TAG,String.valueOf(option));
        startActivity(optionIntent);
    }

}