package com.WMS1.drawful.activities.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
;
import android.os.Bundle;

import com.WMS1.drawful.R;
import com.WMS1.drawful.adapters.VotelistAdapter;
import com.WMS1.drawful.helpers.SharedPrefrencesManager;
import com.WMS1.drawful.views.CanvasView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class VotingActivity extends AppCompatActivity {

    CanvasView canvas;
    VotelistAdapter adapter;
    JSONObject guesses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        canvas = this.findViewById(R.id.canvas);

        canvas.setDrawing(getIntent().getStringExtra("IMAGE"));

        try {
            guesses = new JSONObject(getIntent().getStringExtra("GUESSES"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        initList();

    }

    /**
     * Initializes the list of guesses by coupling the RecyclerView with the adapter and adding the
     * guesses.
     */
    private void initList() {
        HashMap<String, String> map = new HashMap<>();
        String userId = SharedPrefrencesManager.getInstance(getApplicationContext()).getUserId();

        guesses.keys().forEachRemaining(key -> {
            if (!userId.equals(key)) {
                try {
                    map.put(key, (String) guesses.get(key));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        RecyclerView votes = findViewById(R.id.voteList);
        votes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VotelistAdapter(map, getApplicationContext());
        votes.setAdapter(adapter);
    }
}