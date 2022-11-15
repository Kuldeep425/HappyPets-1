package com.example.happypets.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.happypets.Model.Login;
import com.example.happypets.R;
import com.example.happypets.Retrofit.APICall;
import com.example.happypets.Retrofit.RetrofitService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText emailTxt,passwordTxt;
    Button loginBtn;
    public static String userId;
    public static SharedPreferences userDetail;
    public static  String PREFERENCE_DETAIL="Details";
    SharedPreferences.Editor myedit;
    // to initialize all fields
    public void initialize(){
        emailTxt=findViewById(R.id.loginEmail);
        passwordTxt=findViewById(R.id.loginPassword);
        loginBtn=findViewById(R.id.loginButton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();

        // retrofit service
        RetrofitService retrofitService=new RetrofitService();
        APICall apiCall=retrofitService.getRetrofit().create(APICall.class);

        // to login
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=emailTxt.getText().toString().trim();
                String password=passwordTxt.getText().toString().trim();
                if(password.length()==0 || email.length()==0){
                    Toast.makeText(LoginActivity.this, "fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                Login login=new Login(email,password);
                // this is api calling which will take Login model and send to the server
             apiCall.loginUser(login).enqueue(new Callback<String>() {
                 @Override
                 public void onResponse(Call<String> call, Response<String> response) {
                     Toast.makeText(LoginActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                   /* this is working ... Lucky and Apoorv  from here you can change activity like if the login is
                      completed you can go dashboard activity...
                    */
                    // isLoggedIn=true;
                     if(response.body()==null){
                         Toast.makeText(LoginActivity.this, "check entered credentials", Toast.LENGTH_SHORT).show();
                         return;
                     }
                     userId=response.body();
                     userDetail=getSharedPreferences(PREFERENCE_DETAIL,MODE_PRIVATE);
                     myedit=userDetail.edit();
                     myedit.putBoolean("hasLoggedIn",true);
                     myedit.putString("userId",userId);
                     myedit.commit();
                     startActivity(new Intent(LoginActivity.this,MainActivity.class));
                     finish();
                 }

                 @Override
                 public void onFailure(Call<String> call, Throwable t) {
                     System.out.println(t);
                 }
             });
            }
        });
    }

  // go to registration page if don't have account
    public void goToRegistrationPage(View v){
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }
}