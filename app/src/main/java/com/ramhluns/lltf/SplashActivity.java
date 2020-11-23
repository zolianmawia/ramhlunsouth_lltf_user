package com.ramhluns.lltf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    TextView  lltfText, kum, veng, note;
    RelativeLayout relativeLayout;
    Animation txtAnimation,layoutAnimation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        txtAnimation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.fall_down);
        layoutAnimation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.bottom_to_top);


        lltfText = findViewById(R.id.lltfText);
        kum = findViewById(R.id.kum);
        veng = findViewById(R.id.veng);
        note = findViewById(R.id.note);
        relativeLayout = findViewById(R.id.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayout.setAnimation(layoutAnimation);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    lltfText.setVisibility(View.VISIBLE);
                    kum.setVisibility(View.VISIBLE);
                    veng.setVisibility(View.VISIBLE);
                    note.setVisibility(View.VISIBLE);

                    lltfText.setAnimation(txtAnimation);
                    kum.setAnimation(txtAnimation);
                    veng.setAnimation(txtAnimation);
                    note.setAnimation(txtAnimation);
                    }
                },500);
            }
        },0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        },9000);
    }
}
