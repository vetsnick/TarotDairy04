package com.example.tarotdairy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView image = (TextView) findViewById(R.id.splash_text);
//        Animation animation = new TranslateAnimation(0.0f, )
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.my_animation);
        image.startAnimation(animation);


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
                overridePendingTransition(0, 0);

                finish();
            }
        }, 2500);  // 3초 뒤에 Runnable 객체 안의 run 메소드가 실행

    }
}