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

/**
 * Class representing the activity that is shown when the scores are being displayed.
 * Contains a recyclerview with all the current scores.
 */
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

    /**
     * Creates an ArrayList containing Strings with the usernames and scores to be displayed.
     *
     * @param jsonArray Array containing all the users
     * @return  The ArrayList containing the Strings
     * @throws JSONException if jsonArray can't be parsed
     */
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

    /**
     * Initializes the scoreList by coupling the RecyclerView with the adapter and adding the
     * Strings from the scorelist.
     *
     * @param scoreList ArrayList with Strings of usernames and scores
     */
    private void initScoreList(ArrayList<String> scoreList) {
        RecyclerView users = this.findViewById(R.id.scoreList);
        users.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserlistAdapter(this, scoreList);
        users.setAdapter(adapter);
    }
}