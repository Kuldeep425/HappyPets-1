package com.example.happypets.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.happypets.R;

import org.w3c.dom.Text;

public class AllPetsListActivity extends AppCompatActivity {

    private final String TAG = AllPetsListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pets_list);

        //receiving values from intent
        String value = getIntent().getExtras().getInt("SelectedValue")+"";

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.all_pets_recycler_view);

//        MyListAdapter adapter = new MyListAdapter(myListData);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
    }
}