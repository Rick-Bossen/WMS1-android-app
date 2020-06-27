package com.WMS1.drawful.activities.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.WMS1.drawful.R;
import com.WMS1.drawful.helpers.SharedPrefrencesManager;
import com.WMS1.drawful.requests.JwtJsonObjectRequest;
import com.WMS1.drawful.requests.RequestQueueSingleton;
import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

// TODO draw image
public class GuessingActivity extends AppCompatActivity {

    ImageView image;
    TextView guess;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guessing);

        image = this.findViewById(R.id.imageView);
        guess = this.findViewById(R.id.guess);
        button = this.findViewById(R.id.submitButton);
    }

    public void submitGuess(View view) {
        if (!guess.getText().toString().isEmpty()) {
            String id = SharedPrefrencesManager.getInstance(getApplicationContext()).getGameid();
            String url = RequestQueueSingleton.BASE_URL + "/game/" + id + "/theme";
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("guess", guess.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JwtJsonObjectRequest request = new JwtJsonObjectRequest(Request.Method.POST, url, jsonObject, response -> {
                button.setEnabled(false);
                guess.setEnabled(false);
                Toast.makeText(getApplicationContext(),
                        "Guess received",
                        Toast.LENGTH_SHORT).show();
            },
                    error -> Toast.makeText(getApplicationContext(),
                            "Something went wrong, please try again",
                            Toast.LENGTH_SHORT).show(),
                    getApplicationContext());
            RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
        }
    }
}