package com.WMS1.drawful.activities.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.WMS1.drawful.R;

/**
 * Class representing the activity that is shown when the user is waiting for the other players to vote.
 */
public class WaitingForVotingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_for_voting);
    }
}