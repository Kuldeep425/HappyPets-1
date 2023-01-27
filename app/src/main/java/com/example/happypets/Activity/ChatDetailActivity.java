package com.example.happypets.Activity;

import static android.content.ContentValues.TAG;
import static com.example.happypets.Activity.LoginActivity.token;
import static com.example.happypets.Activity.LoginActivity.userId;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.RemoteInput;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happypets.Adapters.ChatDetailsListAdapter;
import com.example.happypets.Adapters.ChatListAdapter;
import com.example.happypets.Model.ChatMessage;
import com.example.happypets.Model.User;
import com.example.happypets.R;
import com.example.happypets.Retrofit.APICall;
import com.example.happypets.Retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;
import okhttp3.Request;
import okhttp3.WebSocketListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompHeader;

public class ChatDetailActivity extends AppCompatActivity implements TextWatcher {

    String userName; // stores the name of the user sending data
    CircleImageView chatUserProfile;
    TextView chatUserName;
    EditText messageEditText;
    Button sendButton;
    RecyclerView messagesRecyclerView;
    private StompClient stompClient;
    String chatUserId;
    APICall apiCall;
    ChatDetailsListAdapter messageAdapter;
    List<ChatMessage>previousMessages;
    final private String SERVER_PATH = "wss://happypets-1-production.up.railway.app/topic/websocket";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        // getting the user id to retrieve its data in the toolbar
        chatUserId=getIntent().getStringExtra("ownerId");
        chatUserProfile=findViewById(R.id.chat_details_user_image);
        chatUserName=findViewById(R.id.chat_details_user_name);
        // this is to hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // calling chatting user data in the toolbar
        RetrofitService retrofitService=new RetrofitService();
         apiCall=retrofitService.getRetrofit().create(APICall.class);
        apiCall.getSpecificInUser(token,chatUserId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(ChatDetailActivity.this,"error in loading user data", Toast.LENGTH_SHORT).show();
                     return;
                }

                User user=response.body();
                if(user.getImageUrl()!=null) {
                    Picasso.get().load(user.getImageUrl()).into(chatUserProfile);
                }
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

       initializeWebsocket();
    }
    // created map to the keep header to pass during establishing the websocket connection
     Map<String,String> m=new HashMap<>();
    // define stompclient to connect websocket
    public void initializeWebsocket(){
        m.put("Authorization",token);
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP,SERVER_PATH,m);
        stompClient.connect();
        stompClient.topic("/user/"+userId+"/queue/messages"+chatUserId).subscribe(topicMessage -> {
            Log.d(TAG, topicMessage.getPayload());
            runOnUiThread(()->{
                JSONObject j= null;

                try {
                    j = new JSONObject(topicMessage.getPayload());
                    JSONObject jj=new JSONObject();
                    jj.put("message",j.getString("message"));
                    jj.put("isSent",false);
                    messageAdapter.addItem(jj);
                    System.out.println(jj);
                    messagesRecyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        });
        stompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    Log.d(TAG, "Stomp connection opened");
                    runOnUiThread(()->{
                        Toast.makeText(this, "connected to the server" , Toast.LENGTH_SHORT).show();
                        System.out.println(stompClient.isConnected());
                        initializeView();
                    });
                    break;

                case ERROR:
                    Log.e(TAG, "Error", lifecycleEvent.getException());
                    System.out.println("Error found in connecting the server");
                    break;

                case CLOSED:
                    Log.d(TAG, "Stomp connection closed");
                    break;
                case FAILED_SERVER_HEARTBEAT:
                    Log.d(TAG,"Error", lifecycleEvent.getException());
                    System.out.println("Heart beats does not match");
                    break;
            }
        });
    }

    private void initializeView(){

        messageEditText = findViewById(R.id.chat_send_message_edit_text);
        sendButton = findViewById(R.id.chat_send_message_button);
        messagesRecyclerView=findViewById(R.id.chat_display_recycler_view);

        // attaching adapter to the recycler view


        messageAdapter = new ChatDetailsListAdapter(getLayoutInflater());
        messagesRecyclerView.setAdapter(messageAdapter);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiCall.getAllPreviousChats(token,userId,chatUserId).enqueue(new Callback<List<ChatMessage>>() {
            @Override
            public void onResponse(Call<List<ChatMessage>> call, Response<List<ChatMessage>> response) {
                previousMessages=response.body();
                Toast.makeText(ChatDetailActivity.this, "found response", Toast.LENGTH_SHORT).show();
                for(int i=0; i<previousMessages.size(); i++){
                       JSONObject j=new JSONObject();
                     try {
                   j.put("name", previousMessages.get(i).getSenderName());
                   j.put("message", previousMessages.get(i).getMessage());
                   boolean flag=userId.equals(previousMessages.get(i).getSenderId());
                         System.out.println(flag);
                   j.put("isSent", flag);
                    messageAdapter.addItem(j);
                    messagesRecyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                }
            catch(Exception e){
                System.out.println(e);
            }
        }
            }

            @Override
            public void onFailure(Call<List<ChatMessage>> call, Throwable t) {

            }
        });


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
                    jsonObject.put("senderId",userId);
                    jsonObject.put("receiverId",chatUserId);
                    jsonObject.put("message", messageEditText.getText().toString());
                    if(messageEditText.getText().toString().length()==0){
                        Toast.makeText(ChatDetailActivity.this, "Please enter message", Toast.LENGTH_SHORT).show();
                         return;
                    }
                   //webSocket.send(jsonObject.toString());
                     stompClient.send("/app/chat",jsonObject.toString()).subscribe();
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