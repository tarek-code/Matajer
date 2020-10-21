package com.unlimited.matajer.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.unlimited.matajer.Auth.login.LoginActivity;
import com.unlimited.matajer.Home.HomeActivity;
import com.unlimited.matajer.MyProfile.SharedManeger;
import com.unlimited.matajer.R;

public class SplashActivity extends AppCompatActivity {
    SharedManeger sharedManeger;
ImageView mImageViewSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
mImageViewSplash=findViewById(R.id.splashSheep);
        Glide.with(SplashActivity.this).load(R.raw.sheep).into(mImageViewSplash);
        sharedManeger=new SharedManeger(SplashActivity.this);
        splashTimer();
    }

    private void splashTimer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
if (sharedManeger.islogin()){
    Intent intent=new Intent(SplashActivity.this, HomeActivity.class);
    startActivity(intent);
    SplashActivity.this.finish();
}
else {
    Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
    startActivity(intent);
    SplashActivity.this.finish();
}
            }
        },3000);
    }
}