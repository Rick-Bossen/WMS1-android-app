package com.WMS1.drawful.activities.general;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.WMS1.drawful.R;
import com.WMS1.drawful.activities.user.LoginActivity;
import com.WMS1.drawful.activities.user.RegisterActivity;

/**
 * Class representing the activity that is shown when the user starts the app without being logged in.
 * Contains buttons to navigate to the login and register activities.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Creates and starts a new LoginActivity.
     *
     * @param view the view to call the function
     */
    public void loginButton(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Creates and starts a new RegisterActivity.
     *
     * @param view the view to call the function
     */
    public void registerButton(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}