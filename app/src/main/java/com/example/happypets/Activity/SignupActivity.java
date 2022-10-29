package com.example.happypets.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happypets.Model.User;
import com.example.happypets.R;
import com.example.happypets.Retrofit.APICall;
import com.example.happypets.Retrofit.RetrofitService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    EditText nameEdtxt,phoneNumberEdtxt,emailEdtxt,passwordEdtxt;
    Button  registerbtn;

    public void initialize(){
        nameEdtxt=findViewById(R.id.registerFullName);
        phoneNumberEdtxt=findViewById(R.id.registerPhoneNumber);
        emailEdtxt=findViewById(R.id.registerEmail);
        passwordEdtxt=findViewById(R.id.registerPassword);
        registerbtn=findViewById(R.id.registerButton);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initialize();

        // retrofit service
        RetrofitService retrofitService=new RetrofitService();
        APICall apiCall=retrofitService.getRetrofit().create(APICall.class);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameEdtxt.getText().toString().trim();
                String email=emailEdtxt.getText().toString().trim();
                String password=passwordEdtxt.getText().toString().trim();
                String phone=phoneNumberEdtxt.getText().toString().trim();
                Toast.makeText(SignupActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                System.out.println("calling the api");
                if(password.length()<6){
                    Toast.makeText(SignupActivity.this, "Password should be minimum 6 Characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(name.length()==0 || email.length()==0 || password.length()==0 || phone.length()==0){
                    Toast.makeText(SignupActivity.this, "complete all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user=new User(name,email,password,phone);
                System.out.println(user.getEmail());
                apiCall.registerUser(user).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(SignupActivity.this, "Please check the email", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.out.println(call.toString());
                        System.out.println(t);
                    }
                });
            }
        });
    }
}