package com.WMS1.drawful.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.WMS1.drawful.helpers.SharedPrefrencesManager;
import com.WMS1.drawful.requests.JwtJsonObjectRequest;
import com.WMS1.drawful.R;
import com.WMS1.drawful.requests.RequestQueueSingleton;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginButton(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void registerButton(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void deleteButton(View view) {
        JwtJsonObjectRequest jsonObjectRequest = new JwtJsonObjectRequest
                (Request.Method.DELETE, RequestQueueSingleton.BASE_URL + "/user/delete", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) { //TODO tokens opslaan en join scherm tonen
                        Toast toast = Toast.makeText(getApplicationContext(), "Response: " + response.toString(), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse.statusCode == 400){
                            Toast toast = Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                }, getApplicationContext());

        RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    public void logoutButton(View view) {
        SharedPrefrencesManager manager = SharedPrefrencesManager.getInstance(getApplicationContext());
        manager.setToken("");
        manager.setRefresh("");
    }
}