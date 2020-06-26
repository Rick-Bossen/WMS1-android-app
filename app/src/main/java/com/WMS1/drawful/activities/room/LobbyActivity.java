package com.WMS1.drawful.activities.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.WMS1.drawful.R;
import com.WMS1.drawful.activities.game.GameActivity;
import com.WMS1.drawful.adapters.UserlistAdapter;
import com.WMS1.drawful.requests.JwtJsonObjectRequest;
import com.WMS1.drawful.requests.RequestQueueSingleton;
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
        try {
            pending();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setCode() {
        code.setText("Join code: " + SharedPrefrencesManager.getInstance(getApplicationContext()).getJoinCode());
    }

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

    private void initUserList() {
        RecyclerView users = findViewById(R.id.userList);
        users.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserlistAdapter(this, userList);
        users.setAdapter(adapter);
    }

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

    private void pending() throws JSONException {
        RequestQueueSingleton queue = RequestQueueSingleton.getInstance(getApplicationContext());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("join_code", SharedPrefrencesManager.getInstance(getApplicationContext()).getJoinCode());

        JwtJsonObjectRequest request = new JwtJsonObjectRequest(Request.Method.GET,
                RequestQueueSingleton.BASE_URL + "/game/pending", jsonObject, response -> {
            try {
                SharedPrefrencesManager.getInstance(getApplicationContext()).setGameId((String) response.get("match_id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
            finishAffinity();
        }, null, getApplicationContext());
        request.setRetryPolicy(new DefaultRetryPolicy(Integer.MAX_VALUE,Integer.MAX_VALUE,1));
        queue.addToRequestQueue(request);
    }


}