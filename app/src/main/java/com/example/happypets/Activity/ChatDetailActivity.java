package com.example.happypets.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happypets.Adapters.ChatDetailsListAdapter;
import com.example.happypets.Model.User;
import com.example.happypets.R;
import com.example.happypets.Retrofit.APICall;
import com.example.happypets.Retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;
import okhttp3.Request;
import okhttp3.WebSocketListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatDetailActivity extends AppCompatActivity implements TextWatcher {

    String userName; // stores the name of the user sending data
    CircleImageView chatUserProfile;
    TextView chatUserName;
    EditText messageEditText;
    Button sendButton;
    RecyclerView messagesRecyclerView;

    // recycler view adapter object
    ChatDetailsListAdapter messageAdapter;

    //creating objects to open web socket
    WebSocket webSocket;
    private String SERVER_PATH = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        // getting the user id to retrieve its data in the toolbar
        String chatUserId=getIntent().getStringExtra("ownerId");
        System.out.println(chatUserId);

        // this is to hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // calling chatting user data in the toolbar
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
                userName = user.getName();
                chatUserName.setText(user.getName());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(call);
                System.out.println(t);
                Toast.makeText(ChatDetailActivity.this, ""+call, Toast.LENGTH_SHORT).show();
            }
        });

        initiateSocketConnection();
    }

    private void initiateSocketConnection() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_PATH).build();
        webSocket = client.newWebSocket(request, new SocketListener());
    }

    // creates inner class which is used to set socket
    private class SocketListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, okhttp3.Response response) {
            super.onOpen(webSocket, response);

            runOnUiThread(() -> {
                Toast.makeText(ChatDetailActivity.this,
                        "Socket Connection Successful!",
                        Toast.LENGTH_SHORT).show();

                initializeView();
            });
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
        }
    }

    private void initializeView(){
        chatUserProfile=findViewById(R.id.chat_details_user_image);
        chatUserName=findViewById(R.id.chat_details_user_name);
        messageEditText = findViewById(R.id.chat_send_message_edit_text);
        sendButton = findViewById(R.id.chat_send_message_button);

        // attaching adapter to the recycler view
        messageAdapter = new ChatDetailsListAdapter(getLayoutInflater());
        messagesRecyclerView.setAdapter(messageAdapter);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // setting text change listener
        messageEditText.addTextChangedListener(this);

        // creating functionality of send button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // basically we are creating a json file with the values and sending it into the web socket
                // we are showing this value in the adapter to show it in recycler view
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name", userName);
                    jsonObject.put("message", messageEditText.getText().toString());

                    // sending json data using websocket
                    webSocket.send(jsonObject.toString());

                    jsonObject.put("isSent", true);
                    messageAdapter.addItem(jsonObject);

                    messagesRecyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

                    resetMessageEdit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    // these methods comes from text watcher interface
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String string = s.toString().trim();

        if (string.isEmpty()) {
            resetMessageEdit();
        }
    }

    // this method is to remove an infinite loop
    private void resetMessageEdit() {
        messageEditText.removeTextChangedListener(this);
        messageEditText.setText("");
        messageEditText.addTextChangedListener(this);
    }

}