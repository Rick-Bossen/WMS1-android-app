package com.WMS1.drawful.activities.game;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.WMS1.drawful.R;

/**
 * Class representing the activity that is shown when the user is waiting for the other player to draw.
 */
public class WaitingForDrawingActivity extends AppCompatActivity {

    TextView waitingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_for_drawing);

        waitingText = findViewById(R.id.waitingText);
        setWaitingText();
    }

    /**
     * Sets the waiting text.
     */
    @SuppressLint("SetTextI18n")
    private void setWaitingText() {
        String username = getIntent().getStringExtra("USER_DRAWING");
        waitingText.setText("Waiting for user: " + username + " to finish drawing.");
    }
}