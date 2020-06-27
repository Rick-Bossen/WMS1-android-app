package com.WMS1.drawful.activities.game;

import androidx.appcompat.app.AppCompatActivity;
;
import android.os.Bundle;
import android.widget.ImageView;

import com.WMS1.drawful.R;
// TODO draw image
// TODO display answers
public class VotingActivity extends AppCompatActivity {

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        image = this.findViewById(R.id.imageView);
    }
}