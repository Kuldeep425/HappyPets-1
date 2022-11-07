package com.example.happypets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.happypets.databinding.ActivityMainBinding;


public class SignupActivity extends AppCompatActivity {

    EditText nameEdtxt,phoneNumberEdtxt,emailEdtxt,passwordEdtxt;
    Button  registerbtn;
    ActivityMainBinding binding;

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

            }
        });
    }





}