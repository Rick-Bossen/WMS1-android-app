package com.WMS1.drawful.activities.game;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.WMS1.drawful.R;

public class WaitingForDrawingActivity extends AppCompatActivity {

    TextView waitingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_for_drawing);

        waitingText = findViewById(R.id.waitingText);
        setWaitingText();
    }

    @SuppressLint("SetTextI18n")
    private void setWaitingText() {
        String username = getIntent().getStringExtra("USER_DRAWING");
        waitingText.setText("Waiting for user: " + username + " to finish drawing.");
    }
}