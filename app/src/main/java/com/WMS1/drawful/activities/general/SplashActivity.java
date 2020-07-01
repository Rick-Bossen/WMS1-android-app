package com.WMS1.drawful.activities.general;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.WMS1.drawful.activities.room.JoinActivity;
import com.WMS1.drawful.helpers.SharedPrefrencesManager;

/**
 * Class representing the splash activity, which is shown when the app is started.
 * Routes the user to the right activity depending on the logged in state.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        routeToActivity();
    }

    /**
     * Starts the MainActivity if no JWT refresh token is found, otherwise starts the JoinActivity.
     */
    private void routeToActivity() {
        Intent intent;
        if (SharedPrefrencesManager.getInstance(getApplicationContext()).getRefresh().isEmpty()) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, JoinActivity.class);
        }
        startActivity(intent);
        finishAffinity();
    }
}