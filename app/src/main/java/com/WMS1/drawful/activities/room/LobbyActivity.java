package com.WMS1.drawful.activities.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.WMS1.drawful.R;
import com.WMS1.drawful.adapters.UserlistAdapter;
import com.WMS1.drawful.requests.JwtJsonObjectRequest;
import com.WMS1.drawful.requests.RequestQueueSingleton;
import com.WMS1.drawful.services.GameHandlerService;
import com.WMS1.drawful.services.RefreshRoomService;
import com.WMS1.drawful.helpers.SharedPrefrencesManager;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;

public class LobbyActivity extends AppCompatActivity {

    TextView code;

    UserlistAdapter adapter;
    ArrayList<String> userList;
    private ScheduledExecutorService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        code = findViewById(R.id.codeText);

        userList = new ArrayList<>();
        initUserList();
        setRefreshService();

        setCode();
        pending();
    }

    /**
     * Sets the join code to display.
     */
    @SuppressLint("SetTextI18n")
    private void setCode() {
        code.setText("Join code: " + SharedPrefrencesManager.getInstance(getApplicationContext()).getJoinCode());
    }

    /**
     * Starts the refresh service to refresh the room in a fixed interval.
     */
    private void setRefreshService() {
        RefreshRoomService service = new RefreshRoomService();
        this.service = service.runService(getApplicationContext(), json -> {
            try {
                updateRoom(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Initiates the userlist by coupling the recyclerview and the adapter.
     */
    private void initUserList() {
        RecyclerView users = findViewById(R.id.userList);
        users.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserlistAdapter(this, userList);
        users.setAdapter(adapter);
    }

    /**
     * Updates the userlist.
     *
     * @param json the json containing the userlist
     * @throws JSONException if json can't be parsed
     */
    private void updateRoom(JSONObject json) throws JSONException {
        JSONArray users = json.getJSONArray("users");
        ArrayList<String> userList = new ArrayList<>();

        for (int i = 0; i < users.length(); i++) {
            JSONObject user = (JSONObject) users.get(i);
            userList.add((String) user.get("username"));
        }
        this.userList.clear();
        this.userList.addAll(userList);
        adapter.notifyDataSetChanged();
    }

    /**
     * Sends pending request and starts game once a response has been received.
     */
    private void pending() {
        RequestQueueSingleton queue = RequestQueueSingleton.getInstance(getApplicationContext());
        SharedPrefrencesManager manager = SharedPrefrencesManager.getInstance(getApplicationContext());

        String url = RequestQueueSingleton.BASE_URL + "/game/pending" + "?join_code=" + manager.getJoinCode();

        JwtJsonObjectRequest request = new JwtJsonObjectRequest(Request.Method.GET, url,
                null, response -> {
            try {
                manager.setGameId(response.getString("match_id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            service.shutdown();
            startService(new Intent(this, GameHandlerService.class));
        }, null, getApplicationContext());
        request.setRetryPolicy(new DefaultRetryPolicy(15 * 60 * 1000,Integer.MAX_VALUE,1));
        queue.addToRequestQueue(request);
    }


}