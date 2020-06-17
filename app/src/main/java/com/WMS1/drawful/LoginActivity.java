package com.WMS1.drawful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText mail, password;
    RequestQueueSingleton queueSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        queueSingleton = RequestQueueSingleton.getInstance(this.getApplicationContext());
        mail = findViewById(R.id.emailField);
        password = findViewById(R.id.passwordField);
    }

    public void loginButton(View view) {
        if (!Validation.validateMail(mail.getText().toString())) {
            Toast toast = Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT);
            toast.show();
        } else if (password.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("mail", mail.getText().toString());
                jsonObject.put("password", password.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST,RequestQueueSingleton.BASE_URL + "/user/login", jsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) { //TODO tokens opslaan en join scherm tonen
                            Toast toast = Toast.makeText(getApplicationContext(), "Response: " + response.toString(), Toast.LENGTH_SHORT);
                            toast.show();
                             SharedPrefrencesManager manager = SharedPrefrencesManager.getInstance(getApplicationContext());
                            try {
                                manager.setToken(response.getString("access_token"));
                                manager.setRefresh(response.getString("refresh"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.networkResponse.statusCode == 400){
                                Toast toast = Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    });
            queueSingleton.addToRequestQueue(jsonObjectRequest);
        }
    }
}