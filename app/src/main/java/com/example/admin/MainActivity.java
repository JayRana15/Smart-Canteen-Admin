package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.lightBlue));

        TextView logo = findViewById(R.id.logo);

        Animation anime = AnimationUtils.loadAnimation(this,R.anim.anime);

        logo.setAnimation(anime);


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int temp = sp.getInt("adminId",100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (temp == 100) {
                    startActivity(new Intent(MainActivity.this,LogInActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(MainActivity.this,DashBoardActivity.class));
                }
            }
        },1500);
    }
}