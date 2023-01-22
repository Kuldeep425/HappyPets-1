package com.example.happypets.Activity;

import static com.example.happypets.Activity.LoginActivity.token;
import static com.example.happypets.Activity.LoginActivity.userId;

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
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.happypets.R;

public class SPlashScreenActivity extends Activity {
    private ImageView logo;
    private TextView title,slogan;
    public static boolean isLoggedIn=false;
    Animation topAnimation,bottomAnimation;
    public static SharedPreferences userInfo;
    private final String CHANNEL_ID="WelcomeChannel";
    private final int NOTIFICATION_INTEGER=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        logo = findViewById(R.id.logo);
        title = findViewById(R.id.title);
        slogan = findViewById(R.id.slogan);
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        logo.setAnimation(topAnimation);
        title.setAnimation(bottomAnimation);
        slogan.setAnimation(bottomAnimation);


        //notification to welcome user
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
        int SPLASH_SCREEN = 4500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                userInfo=getSharedPreferences(LoginActivity.PREFERENCE_DETAIL,MODE_PRIVATE);
                boolean isLoggedIn=userInfo.getBoolean("hasLoggedIn",false);
                userId=userInfo.getString("userId",null);
                token=userInfo.getString("token",null);
                if(isLoggedIn && userId!=null && token!=null) intent=new Intent(SPlashScreenActivity.this,MainActivity.class);
                else
                intent = new Intent(SPlashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN );

    }
}
