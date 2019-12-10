package com.example.goalwise.activity;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.goalwise.R;

public class SplashActivity extends AppCompatActivity {

    private static final int DELAY_MS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sleepAndContinue(DELAY_MS);
    }

    private void dispatchActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void sleepAndContinue(int delay) {
        Runnable activityStart = new Runnable() {
            @Override
            public void run() {
                dispatchActivity();
            }
        };
        new Handler().postDelayed(activityStart, delay);
    }
}
