package com.WMS1.drawful.activities.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.WMS1.drawful.R;
import com.WMS1.drawful.activities.room.JoinActivity;
import com.WMS1.drawful.requests.RequestQueueSingleton;
import com.WMS1.drawful.helpers.SharedPrefrencesManager;
import com.WMS1.drawful.helpers.Validation;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class representing the activity that is shown when the user has to log in.
 */
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

    /**
     * Tries to log in the user using the entered credentials.
     * Displays an error message when credentials are incorrect or the user is not found.
     * Starts a new JoinActivity if the user has successfully logged in.
     *
     * @param view the view to call the function
     */
    public void loginButton(View view) {
        if (!Validation.validateMail(mail.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT).show();
        } else {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("mail", mail.getText().toString());
                jsonObject.put("password", password.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST,RequestQueueSingleton.BASE_URL + "/user/login", jsonObject, response -> {
                        try {
                            loginUser(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, JoinActivity.class);
                        startActivity(intent);
                        finishAffinity();

                    }, error -> {
                        if (error.networkResponse.statusCode == 400){
                            Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_LONG).show();
                        }
                    });
            queueSingleton.addToRequestQueue(jsonObjectRequest);
        }
    }

    /**
     * Adds the jwt token, refresh token and user id from the response to the sharedprefrences.
     *
     * @param response response containing all the data
     * @throws JSONException if response can't be parsed
     */
    private void loginUser(JSONObject response) throws JSONException {
        SharedPrefrencesManager manager = SharedPrefrencesManager.getInstance(getApplicationContext());
            manager.setToken(response.getString("access_token"));
            manager.setRefresh(response.getString("refresh_token"));


        DecodedJWT jwt = JWT.decode(response.getString("access_token"));
        String userId = (String) jwt.getClaim("identity").asMap().get("_id");
        SharedPrefrencesManager.getInstance(getApplicationContext()).setUserId(userId);
    }
}