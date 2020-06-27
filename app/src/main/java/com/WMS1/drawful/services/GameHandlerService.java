package com.WMS1.drawful.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.WMS1.drawful.activities.game.DrawingActivity;
import com.WMS1.drawful.activities.game.GameFinishedActivity;
import com.WMS1.drawful.activities.game.GuessingActivity;
import com.WMS1.drawful.activities.game.VoteResultsActivity;
import com.WMS1.drawful.activities.game.VotingActivity;
import com.WMS1.drawful.activities.game.WaitingForDrawingActivity;
import com.WMS1.drawful.helpers.SharedPrefrencesManager;
import com.WMS1.drawful.requests.JwtJsonObjectRequest;
import com.WMS1.drawful.requests.RequestQueueSingleton;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameHandlerService extends Service {

    private static String baseUrl;
    private RequestQueueSingleton queue;
    private Response.Listener<JSONObject> listener;

    private String userId;
    private String gameId;

    private boolean isActive;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("GameHandler start command");
        getValues();
        queue = RequestQueueSingleton.getInstance(getApplicationContext());
        baseUrl = RequestQueueSingleton.BASE_URL + "/game/" + gameId + "/status";
        isActive = true;

        initListener();
        getStatus(false);

        return START_STICKY;
    }
    @Override
    public void onDestroy() {

    }

    private void getStatus(boolean longPolling) {
        String url;
        if (longPolling) {
            url = baseUrl + "?after=" + System.currentTimeMillis() / 1000L;
            System.out.println(url);
        } else {
            url = baseUrl;
        }
        JwtJsonObjectRequest request = new JwtJsonObjectRequest(Request.Method.GET, url,
                null, listener, null, getApplicationContext());
        request.setRetryPolicy(new DefaultRetryPolicy(2 * 60 * 1000, 10,1));
        queue.addToRequestQueue(request);
    }

    private void initListener() {
        listener = response -> {
            try {
                updateGame(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (isActive)
            getStatus(true);
        };
    }

    private void getValues() {
        SharedPrefrencesManager manager = SharedPrefrencesManager.getInstance(getApplicationContext());
        userId = manager.getUserId();
        gameId = manager.getGameid();
    }

    private void updateGame(JSONObject data) throws JSONException {
        String status = data.getString("status");
        Intent intent = null;

        JSONArray unresponsiveUsers = data.getJSONArray("unresponsive_users");

        for (int i = 0; i < unresponsiveUsers.length(); i++) {
            if (userId.equals(unresponsiveUsers.get(i))) {
                //TODO add pop up if user is unresponsive
            }
        }

        switch (status) {
            case "drawing":

                if (data.get("user_drawing").equals(userId)) {
                    intent = new Intent(this, DrawingActivity.class);
                } else {
                    intent = new Intent(this, WaitingForDrawingActivity.class);
                }
                break;
            case "guessing":
                intent = new Intent(this, GuessingActivity.class);
                intent.putExtra("IMAGE", data.getString("drawing"));
                break;
            case "voting":
                intent = new Intent(this, VotingActivity.class);
                intent.putExtra("IMAGE", data.getString("drawing"));
                intent.putExtra("GUESSES", data.getJSONObject("guesses").toString());
                break;
            case "showing_scores":
                intent = new Intent(this, VoteResultsActivity.class);
                intent.putExtra("GUESSES", data.getJSONObject("guesses").toString());
                intent.putExtra("VOTES", data.getJSONObject("votes").toString());
                intent.putExtra("USERS", data.getJSONArray("users").toString());
                break;
            case "finished":
                intent = new Intent(this, GameFinishedActivity.class);
                intent.putExtra("USERS", data.getJSONArray("users").toString());
                isActive = false;
                break;
        }

        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
