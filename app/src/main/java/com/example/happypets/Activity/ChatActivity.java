package com.example.happypets.Activity;

import static com.example.happypets.Activity.LoginActivity.token;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.example.happypets.Adapters.ChatListAdapter;
import com.example.happypets.Model.User;
import com.example.happypets.R;
import com.example.happypets.Retrofit.APICall;
import com.example.happypets.Retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    // creating variables
    private RecyclerView chatRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_new);
        chatRecyclerView = findViewById(R.id.chat_recycler_view);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // changing the title of chat
        getSupportActionBar().setTitle("Chat User List");

        // making retrofit object
        RetrofitService retrofitService=new RetrofitService();
        APICall apiCall=retrofitService.getRetrofit().create(APICall.class);

        apiCall.getAllUser(token).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
               if(!response.isSuccessful()){
                   Toast.makeText(ChatActivity.this, "error in loading users data", Toast.LENGTH_SHORT).show();
                   System.out.println(response.body());
                   return;
               }
               List<User>userList=response.body();
               ChatListAdapter chatListAdapter=new ChatListAdapter(userList,ChatActivity.this);
               chatRecyclerView.setAdapter(chatListAdapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

    }
}