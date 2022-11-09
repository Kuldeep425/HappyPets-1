package com.example.happypets.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happypets.Model.User;
import com.example.happypets.R;
import com.example.happypets.Retrofit.APICall;
import com.example.happypets.Retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatDetailActivity extends AppCompatActivity {
    CircleImageView chatUserProfile;
    TextView chatUserName;

    private void initialize(){
        chatUserProfile=findViewById(R.id.chat_details_user_image);
        chatUserName=findViewById(R.id.chat_details_user_name);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        initialize();
        String chatUserId=getIntent().getStringExtra("ownerId");
        System.out.println(chatUserId);
        // this is to hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // method to all Chatting user data
        RetrofitService retrofitService=new RetrofitService();
        APICall apiCall=retrofitService.getRetrofit().create(APICall.class);
        apiCall.getSpecificInUser(chatUserId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(ChatDetailActivity.this,"error in loading user data", Toast.LENGTH_SHORT).show();
                     return;
                }
                User user=response.body();
                Picasso.get().load(user.getImageUrl()).into(chatUserProfile);
                chatUserName.setText(user.getName());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(call);
                System.out.println(t);
                Toast.makeText(ChatDetailActivity.this, ""+call, Toast.LENGTH_SHORT).show();
            }
        });
    }
}