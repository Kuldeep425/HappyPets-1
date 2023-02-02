package com.example.happypets.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.happypets.Model.User;
import com.example.happypets.R;
import com.example.happypets.Retrofit.APICall;
import com.example.happypets.Retrofit.RetrofitService;
import com.example.happypets.Utils.RealPathUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignupActivity extends AppCompatActivity {

    private final String TAG = SignupActivity.class.getName();

    private EditText nameEdtxt,emailEdtxt,passwordEdtxt;
    private ImageView registerbtn;
    private ProgressDialog progressDialog;

    private String name, email, password;

    // to initialize the components
    public void initialize(){
        nameEdtxt=findViewById(R.id.user_signin_profile_name);
        emailEdtxt=findViewById(R.id.user_signin_profile_email);
        passwordEdtxt=findViewById(R.id.user_signin_profile_password);
        registerbtn=findViewById(R.id.user_signin_button);
    }


    public void progressDialogOpen(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_new);
        initialize();
        
        // removing action bar
        getSupportActionBar().hide();

        // creating retrofit service
        RetrofitService retrofitService = new RetrofitService();
        APICall apiCall = retrofitService.getRetrofit().create(APICall.class);

        //obtaining values from the edit text
        name = nameEdtxt.getText().toString();
        email = emailEdtxt.getText().toString();
        password = passwordEdtxt.getText().toString();

        // setting functionality of the register button
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //checking input
                boolean fieldsCorrect = checkUserInput();
                if(fieldsCorrect){
                    // open progress dialog
                    progressDialogOpen();
                    // creating user object to send
                    User user = new User(name,email,password);
                    // calling api
                    apiCall.signupUser(user).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                            // when user response is successful
                            if(response.isSuccessful()){
                                progressDialog.dismiss();
                                showAlertDialogmessageOnResponse();
                                makeEmptyToAllfield();

                            }
                        }
                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            progressDialog.dismiss();
                            showAlertDialogmessageOnErrorResponse();
                        }
                    });
                }
            }
        });
    }

    // this method is to check if the input given by the user is valid
    private boolean checkUserInput(){
        boolean notEmptyField = true;
        if(name.length()==0) notEmptyField = false;
        else if(email.length()==0) notEmptyField = false;
        // checking password
        else if(password.length()==0) notEmptyField = false;

        boolean ncheck=true, ccheck=true, capcheck=true;
        for(char ch : password.toCharArray()){
            if(ch>='a' && ch<='z') ccheck=false;
            else if(ch>='0' && ch<='9') ncheck=false;
            else if(ch>='A' && ch<='Z') capcheck = false;
        }
        boolean notRightPassword = ncheck && ccheck && capcheck && (password.length()<6);

        //showing the toast message
        if(!notEmptyField) Toast.makeText(SignupActivity.this,  "Please fill empty fields", Toast.LENGTH_SHORT).show();

        if(!notRightPassword) Toast.makeText(SignupActivity.this, "Password should be more than 6 digits. \nIt should have a " +
                "capital letter, a number and a small letter", Toast.LENGTH_SHORT);

        return notEmptyField && notRightPassword;

    }

    // method to make empty to all fields if verification link has been sent
    private void makeEmptyToAllfield(){
        nameEdtxt.setText("");
        emailEdtxt.setText("");
        passwordEdtxt.setText("");
    }


  // this is the method which will show alert dialog message if there is error in response
    private void showAlertDialogmessageOnErrorResponse() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("\t\tError");
        builder.setIcon(R.drawable.cancelicon);
        builder.setMessage("\t\tSomething went wrong\n"+"\t\tor\n"+"\t\tEmail already registered");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Back",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    // this will execute when api give right response
    private void showAlertDialogmessageOnResponse() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Successfully sent");
        builder.setIcon(R.drawable.rightcheck);
        builder.setMessage("We have sent verification link on "+nameEdtxt.getText().toString()+"\n\nplease verify for further process..\nPlease verify and login");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}