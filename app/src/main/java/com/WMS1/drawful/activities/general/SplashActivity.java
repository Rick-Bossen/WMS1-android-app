package com.WMS1.drawful.activities.general;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.WMS1.drawful.activities.room.JoinActivity;
import com.WMS1.drawful.helpers.SharedPrefrencesManager;

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