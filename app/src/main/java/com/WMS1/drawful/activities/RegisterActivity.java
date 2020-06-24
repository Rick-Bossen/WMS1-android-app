package com.WMS1.drawful.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.WMS1.drawful.R;
import com.WMS1.drawful.requests.RequestQueueSingleton;
import com.WMS1.drawful.helpers.Validation;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    EditText username, mail, password;
    RequestQueueSingleton queueSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        queueSingleton = RequestQueueSingleton.getInstance(this.getApplicationContext());
        username = findViewById(R.id.usernameField);
        mail = findViewById(R.id.emailField);
        password = findViewById(R.id.passwordField);
    }

    public void registerButton(View view) {
        if (!Validation.validateUsername(username.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Invalid username", Toast.LENGTH_SHORT).show();
        } else if (!Validation.validateMail(mail.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT).show();

        } else {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", username.getText().toString());
                jsonObject.put("mail", mail.getText().toString());
                jsonObject.put("password", password.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST,RequestQueueSingleton.BASE_URL + "/user/create" , jsonObject, response -> {
                        Toast.makeText(getApplicationContext(), "User created successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }, error -> {
                        if (error.networkResponse.statusCode == 400){
                            Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_LONG).show();
                        }
                    });
            queueSingleton.addToRequestQueue(jsonObjectRequest);
        }
    }
}