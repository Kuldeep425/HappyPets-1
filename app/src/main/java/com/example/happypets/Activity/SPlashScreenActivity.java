package com.example.happypets.Activity;

import static com.example.happypets.Activity.LoginActivity.token;
import static com.example.happypets.Activity.LoginActivity.userId;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.happypets.R;

public class SPlashScreenActivity extends Activity {
    private LottieAnimationView animationView;
    private ImageView textGif;
    public static boolean isLoggedIn=false;
    public static SharedPreferences userInfo;
    private final String CHANNEL_ID="WelcomeChannel";
    private final int NOTIFICATION_INTEGER=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen_new);

        //hooking layouts
        animationView = findViewById(R.id.splash_screen_lottie_animation);


        //notification which is sent when the app is started
        //this notification is just to be used in the front end and does not require firebase
        Drawable drawable= ResourcesCompat.getDrawable(getResources(),R.drawable.petlogo,null);
        BitmapDrawable bitmapDrawable= (BitmapDrawable) drawable;
        Bitmap largeIcon=bitmapDrawable.getBitmap();

        Notification.BigPictureStyle bigPictureStyle=new Notification.BigPictureStyle()
                .bigPicture(((BitmapDrawable) (ResourcesCompat.getDrawable(getResources(),R.drawable.welcomenotification,null))).getBitmap())
                .bigLargeIcon(largeIcon)
                .setBigContentTitle("Welcome")
                .setSummaryText("let's do it");
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification=new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.petlogo)
                    .setContentText("Welcome to my app")
                    .setSubText("New Message")
                    .setStyle(bigPictureStyle)
                    .setChannelId(CHANNEL_ID)
                    .build();
            notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"WelcomeUser",NotificationManager.IMPORTANCE_HIGH));
        }
        else{
            notification=new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.petlogo)
                    .setContentText("Welcome to my app")
                    .setSubText("New Message ")
                    .build();
        }
        notificationManager.notify(NOTIFICATION_INTEGER,notification);

        // splash screen
        final int SPLASH_SCREEN_TIME =5000;
        /*
        * handler class is used for updating the ui thread from another thread
        * here we are checking if we are already logged in or not
        * if already logged in then we will directly enter the app
        * after that login page will be visible
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                userInfo=getSharedPreferences(LoginActivity.PREFERENCE_DETAIL,MODE_PRIVATE);
                boolean isLoggedIn=userInfo.getBoolean("hasLoggedIn",false);
                userId=userInfo.getString("userId",null);
                token=userInfo.getString("token",null);
                if(isLoggedIn && userId!=null && token!=null)
                    intent=new Intent(SPlashScreenActivity.this,MainActivity.class);
                else
                    intent = new Intent(SPlashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN_TIME );

    }
}
