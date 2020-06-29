package com.WMS1.drawful.activities.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
;
import android.os.Bundle;
import android.widget.ImageView;

import com.WMS1.drawful.R;
import com.WMS1.drawful.adapters.AnswerlistAdapter;
import com.WMS1.drawful.adapters.VotelistAdapter;
import com.WMS1.drawful.views.CanvasView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

// TODO improve layout
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

    private void initList() {
        HashMap<String, String> map = new HashMap<>();

        guesses.keys().forEachRemaining(key -> {
            try {
                map.put(key, (String) guesses.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        RecyclerView votes = findViewById(R.id.voteList);
        votes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VotelistAdapter(map, getApplicationContext());
        votes.setAdapter(adapter);
    }
}