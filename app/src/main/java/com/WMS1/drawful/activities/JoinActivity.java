package com.WMS1.drawful.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.WMS1.drawful.R;
import com.WMS1.drawful.helpers.SharedPrefrencesManager;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
    }

    public void logoutButton(View view) {
        SharedPrefrencesManager manager = SharedPrefrencesManager.getInstance(getApplicationContext());
        manager.setToken("");
        manager.setRefresh("");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}