package com.example.happypets.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.example.happypets.Model.Login;
import com.example.happypets.Model.LoginResponse;
import com.example.happypets.R;
import com.example.happypets.Retrofit.APICall;
import com.example.happypets.Retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText emailTxt,passwordTxt;
    private ImageView loginBtn;
    private TextView signInText;

    public static String userId;
    public static SharedPreferences userDetail;
    public static  String PREFERENCE_DETAIL="Details";
    public static String token=null;
    SharedPreferences.Editor myedit;
    ProgressDialog progressDialog;
    // to initialize all fields
    public void initialize(){
        emailTxt=findViewById(R.id.loginEmail);
        passwordTxt=findViewById(R.id.loginPassword);
        loginBtn=findViewById(R.id.loginButton);
        signInText=findViewById(R.id.textView2);
    }
    // open progress
    public void progressDialogOpen(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();

        //removing action bar
        getSupportActionBar().hide();

        //setting listener to the sign in redirecting text
        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            }
        });

        // retrofit service
        RetrofitService retrofitService=new RetrofitService();
        APICall apiCall=retrofitService.getRetrofit().create(APICall.class);

         //to login
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailTxt.getText().toString().trim();
                String password=passwordTxt.getText().toString().trim();
                if(password.length()==0 || email.length()==0){
                    Toast.makeText(LoginActivity.this, "fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                // open dialog box
                 progressDialogOpen();
                // making login object to store login data
                Login login=new Login(email,password);

                // this is api calling which will take Login model and send to the server
             apiCall.loginUser(login).enqueue(new Callback<LoginResponse>() {
                 @Override
                 public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                     if(response.body()==null){
                         Toast.makeText(LoginActivity.this, "email or password is not correct", Toast.LENGTH_SHORT).show();
                          progressDialog.dismiss();
                         return;
                     }
                     /*
                      * we are creating a shared preference which will store key value pair to be shared in different
                      * activities
                      * it stores these key value pairs in local storage
                      */
                     LoginResponse loginResponse =response.body();
                     // creating a shared preference
                     userDetail=getSharedPreferences(PREFERENCE_DETAIL,MODE_PRIVATE);
                     myedit=userDetail.edit();
                     try {
                         myedit.putBoolean("hasLoggedIn", true);
                         myedit.putString("userId", loginResponse.getId());
                         myedit.putString("token", "Bearer "+loginResponse.getToken());
                         token="Bearer "+loginResponse.getToken();
                     }
                     catch(Exception e){
                         System.out.println(e);
                     }
                     myedit.commit();
                     // starting login acitivity
                     startActivity(new Intent(LoginActivity.this,MainActivity.class));

                     progressDialog.dismiss();

                     finish();
                 }

                 @Override
                 public void onFailure(Call<LoginResponse> call, Throwable t) {
                     progressDialog.dismiss();
                 }
             });
            }
        });
    }
}