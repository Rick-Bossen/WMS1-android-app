package com.WMS1.drawful.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.WMS1.drawful.R;
import com.WMS1.drawful.helpers.SharedPrefrencesManager;
import com.WMS1.drawful.requests.RefreshJsonObjectRequest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        routeToActivity();
    }

    private void routeToActivity() {
        Intent intent;
        if (SharedPrefrencesManager.getInstance(getApplicationContext()).getRefresh().isEmpty()) {
            intent = new Intent(this, MainActivity.class);
        } else { //TODO Refresh token on boot
            intent = new Intent(this, JoinActivity.class);
        }
        startActivity(intent);
        finishAffinity();
    }
}