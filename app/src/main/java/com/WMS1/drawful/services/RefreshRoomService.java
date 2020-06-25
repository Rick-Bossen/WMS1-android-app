package com.WMS1.drawful.services;

import android.content.Context;

import com.WMS1.drawful.helpers.SharedPrefrencesManager;
import com.WMS1.drawful.requests.JwtJsonObjectRequest;
import com.WMS1.drawful.requests.RequestQueueSingleton;
import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONObject;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class RefreshRoomService {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public ScheduledExecutorService runService(Context context, Response.Listener<JSONObject> listener) {
        final Runnable runnable = () -> {
            if (!SharedPrefrencesManager.getInstance(context).getJoinCode().isEmpty()) {
                RequestQueueSingleton queueSingleton = RequestQueueSingleton.getInstance(context);
                JwtJsonObjectRequest request = new JwtJsonObjectRequest(Request.Method.GET,
                        RequestQueueSingleton.BASE_URL + "/room/" + SharedPrefrencesManager.getInstance(context).getJoinCode(), null,
                        listener, error -> {
                    if (error.networkResponse.statusCode == 404){
                        scheduler.shutdown();
                    }
                }, context);
                queueSingleton.addToRequestQueue(request);
            }
        };
        final ScheduledFuture<?> RefreshHandle =
                scheduler.scheduleAtFixedRate(runnable, 0, 5, SECONDS);
        return scheduler;
    }
}
