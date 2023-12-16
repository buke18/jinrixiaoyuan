package com.dancun.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.dancun.R;
import com.dancun.entity.BaseEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_LENGTH =1000 ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                //关闭splashActivity，避免按返回键返回此界面
                SplashActivity.this.finish();
            }
        }, SPLASH_LENGTH);

    }
}