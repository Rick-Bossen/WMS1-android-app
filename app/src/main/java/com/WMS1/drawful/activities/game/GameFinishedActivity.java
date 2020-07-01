package com.WMS1.drawful.activities.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.WMS1.drawful.R;
import com.WMS1.drawful.activities.room.JoinActivity;

public class GameFinishedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_finished);
    }

    /**
     * Returns to the JoinActivity and removes all previous activities from the stack
     *
     * @param view the view to call the function
     */
    public void toJoinActivity(View view) {
        Intent intent = new Intent(this, JoinActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}