package com.WMS1.drawful.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.WMS1.drawful.R;
import com.WMS1.drawful.helpers.AnswersAdapter;

public class TempActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);


        RecyclerView view = findViewById(R.id.resultsView);
        view.setLayoutManager(new LinearLayoutManager(this));
        AnswersAdapter adapter = new AnswersAdapter();
        view.setAdapter(adapter);
    }
}