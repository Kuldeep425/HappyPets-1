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

public class SignupActivity extends AppCompatActivity {

    EditText nameEdtxt,phoneNumberEdtxt,emailEdtxt,passwordEdtxt;
    Button  registerbtn;
    CircleImageView signUpProfileImage;
    String path;
    Uri selectedImageUri;
    Bitmap selectedImageBitmap;
    ProgressDialog progressDialog;
    CardView cardView;

    // to initialize the components
    public void initialize(){
        nameEdtxt=findViewById(R.id.registerFullName);
        phoneNumberEdtxt=findViewById(R.id.registerPhoneNumber);
        emailEdtxt=findViewById(R.id.registerEmail);
        passwordEdtxt=findViewById(R.id.registerPassword);
        registerbtn=findViewById(R.id.registerButton);
        signUpProfileImage=findViewById(R.id.profile_image);
        cardView=findViewById(R.id.cardViewSignup);
        cardView.setTranslationY(2000);
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
        setContentView(R.layout.activity_signup);
        initialize();
        cardView.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(900).start();
        //choose profile picture from gallery

        signUpProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageFromGallery();
            }
        });

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
                progressDialogOpen();
                User user=new User(name,email,password,phone);
                // image
                File image=new File(path);
                RequestBody requestFile=RequestBody.create(MediaType.parse("multipart/form-data"),image);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", image.getName(), requestFile);
                apiCall.registerUser(body,user).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(SignupActivity.this, "Please check the email", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        showAlertDialogmessageOnResponse();
                        makeEmptyToAllfield();
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.out.println(call.toString());
                        progressDialog.dismiss();
                        showAlertDialogmessageOnErrorResponse();
                        System.out.println(t);
                    }
                });
            }
        });
    }
    // method to make empty to all fields if verification link has been sent

    private void makeEmptyToAllfield(){
        nameEdtxt.setText("");
        emailEdtxt.setText("");
        passwordEdtxt.setText("");
        phoneNumberEdtxt.setText("");
        signUpProfileImage.setImageDrawable(getResources().getDrawable(R.drawable.profile_image));
    }


    // to choose a user photo from gallery
    public void chooseImageFromGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(i);
    }
    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null
                            && data.getData() != null) {
                        selectedImageUri = data.getData();
                        Context context=SignupActivity.this;
                        path= RealPathUtil.getRealPath(context,selectedImageUri);
                        System.out.println(path);
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        signUpProfileImage.setImageBitmap(selectedImageBitmap);
                    }
                }
            });

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
        builder.setMessage("We have sent verification link on "+nameEdtxt.getText().toString()+"\n\nplease verify for further process..😊😊\nPlease verify and login");
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