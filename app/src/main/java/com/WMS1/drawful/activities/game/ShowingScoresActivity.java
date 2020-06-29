package com.WMS1.drawful.activities.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.WMS1.drawful.R;
import com.WMS1.drawful.adapters.UserlistAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowingScoresActivity extends AppCompatActivity {

    UserlistAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing_scores);

        try {
            JSONArray jsonArray = new JSONArray(getIntent().getStringExtra("USERS"));
            ArrayList<String> scoreList = createScoreList(jsonArray);
            initScoreList(scoreList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> createScoreList(JSONArray jsonArray) throws JSONException {
        HashMap<String, Integer> scores = new HashMap<>();
        ArrayList<String> scoreStrings = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            scores.put(jsonArray.getJSONObject(i).getString("username"),
                       jsonArray.getJSONObject(i).getInt("score"));
        }

        scores.forEach((key, value) -> {
            String string = key + ": " + value;
            scoreStrings.add(string);
        });

        return scoreStrings;
    }

    private void initScoreList(ArrayList<String> scoreList) {
        RecyclerView users = this.findViewById(R.id.scoreList);
        users.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserlistAdapter(this, scoreList);
        users.setAdapter(adapter);
    }
}