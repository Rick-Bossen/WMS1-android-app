package com.WMS1.drawful.activities.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.WMS1.drawful.R;
import com.WMS1.drawful.adapters.AnswerlistAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Class representing the activity that is shown when the vote results are displayed.
 * Contains a recyclerview with all the vote results.
 */
public class VoteResultsActivity extends AppCompatActivity {

    AnswerlistAdapter adapter;
    JSONObject votes;
    JSONObject guesses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_results);

        try {
            votes = new JSONObject(getIntent().getStringExtra("VOTES"));
            guesses = new JSONObject(getIntent().getStringExtra("GUESSES"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        initResultList();
    }

    /**
     * Initializes the result list by coupling the RecyclerView with the adapter and adding the
     * vote results.
     */
    private void initResultList() {
        HashMap<String, Integer> answers = new HashMap<>();
        AtomicReference<String> correct = new AtomicReference<>("");

        guesses.keys().forEachRemaining(guessKey -> {
            AtomicInteger counter = new AtomicInteger();

            votes.keys().forEachRemaining(voteKey -> {
                try {
                    if (votes.getString(voteKey).equals(guessKey)) {
                        counter.getAndIncrement();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            try {
                answers.put(guesses.getString(guessKey), counter.intValue());

                if (guessKey.equals("answer")) {
                    correct.set(guesses.getString(guessKey));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

        RecyclerView users = findViewById(R.id.userList);
        users.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AnswerlistAdapter(answers, correct.get(), getApplicationContext());
        users.setAdapter(adapter);
    }
}