package com.WMS1.drawful.activities.room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.WMS1.drawful.R;
import com.WMS1.drawful.activities.general.MainActivity;
import com.WMS1.drawful.helpers.SharedPrefrencesManager;
import com.WMS1.drawful.helpers.Validation;
import com.WMS1.drawful.requests.JwtJsonObjectRequest;
import com.WMS1.drawful.requests.RequestQueueSingleton;
import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinActivity extends AppCompatActivity {

    EditText code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        code = findViewById(R.id.codeField);
    }

    public void logoutButton(View view) {
        SharedPrefrencesManager manager = SharedPrefrencesManager.getInstance(getApplicationContext());
        manager.setToken("");
        manager.setRefresh("");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void joinButton(View view) {
        if (!Validation.validateJoinCode(code.getText().toString().toUpperCase())) {
            Toast.makeText(getApplicationContext(), "Invalid code", Toast.LENGTH_LONG).show();
        } else {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("join_code", code.getText().toString().toUpperCase());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestQueueSingleton queueSingleton = RequestQueueSingleton.getInstance(getApplicationContext());
            JwtJsonObjectRequest request = new JwtJsonObjectRequest(Request.Method.POST, RequestQueueSingleton.BASE_URL + "/room/join",
                    jsonObject, response -> {
                SharedPrefrencesManager.getInstance(getApplicationContext()).setJoinCode(code.getText().toString().toUpperCase());
                Intent intent = new Intent(this, LobbyActivity.class);
                startActivity(intent);
                finishAffinity();
            }, error -> {
                if (error.networkResponse.statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Room not found", Toast.LENGTH_LONG).show();
                } else if (error.networkResponse.statusCode == 403) {
                    Toast.makeText(getApplicationContext(), "Room is full or user is already in room", Toast.LENGTH_LONG).show();
                }
            }, getApplicationContext());

            queueSingleton.addToRequestQueue(request);
        }
    }
}